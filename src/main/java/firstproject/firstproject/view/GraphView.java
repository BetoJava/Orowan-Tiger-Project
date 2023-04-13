package firstproject.firstproject.view;

import firstproject.firstproject.Main;
import firstproject.firstproject.assets.Assets;
import firstproject.firstproject.controller.H2Database;
import firstproject.firstproject.model.Orowan;
import firstproject.firstproject.model.ProcessedOutputData;
import firstproject.firstproject.model.Stand;
import firstproject.firstproject.model.Strip;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class GraphView extends View {

    private HBox radioButtonRow = new HBox();
    private Label titleLabel = new Label("Graphics");

    private LineChart<Number, Number> lineChart;
    private XYChart.Series<Number, Number> seriesFriction;
    private XYChart.Series<Number, Number> seriesSigma;
    private XYChart.Series<Number, Number> seriesRollSpeed;
    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    private Label computeTimeLabel;
    private CheckBox checkBoxRollSpeed;
    private CheckBox checkBoxFriction;
    private CheckBox checkBoxSigma;
    private Button menuButton;

    private HBox hBoxComboBoxes = new HBox();
    private ComboBox<String> standComboBox = new ComboBox<>();
    private ComboBox<String> stripIDComboBox = new ComboBox<>();

    /**
     * Constructeur de la View graphique
     *
     * @param root         Noeud racine sur lequel se fait l'affichage
     * @param primaryStage PrimaryStage
     */
    public GraphView(VBox root, Stage primaryStage) {
        super(root, primaryStage);

        createButtons(root);
        createComboBoxes();
        createGraph();
        createScene(root);
        customComponents(root);
    }

    private void createComboBoxes() {
        for(Stand s : Main.getCurrentUser().getStandList()) {
            standComboBox.getItems().add(s.getStandID());
            for(Strip strip : s.getStripList()) {
                stripIDComboBox.getItems().add(String.valueOf(strip.getStripID()));
            }
        }

        standComboBox.setOnAction(e -> addDataToGraph());
        stripIDComboBox.setOnAction(e -> addDataToGraph());
    }

    private ArrayList<ProcessedOutputData> getProcessedOutputData(int stripID, String stand) {
        return H2Database.getInstance().loadProcessedOutputData(stripID, stand);
    }

    private void addDataToGraph() {

        int stripID = Integer.parseInt(stripIDComboBox.getValue());
        String stand = standComboBox.getValue();
        seriesFriction.getData().clear();
        seriesRollSpeed.getData().clear();
        seriesSigma.getData().clear();
        ArrayList<ProcessedOutputData> processedOutputData = getProcessedOutputData(stripID, stand);
        for(int i = 0; i < processedOutputData.size(); i++) {
            ProcessedOutputData data = processedOutputData.get(i);
            seriesFriction.getData().add(new XYChart.Data<>(i* ProcessedOutputData.xTimeMS, data.getFriction()));
            seriesRollSpeed.getData().add(new XYChart.Data<>(i*ProcessedOutputData.xTimeMS, data.getRollingSpeed()));
            seriesSigma.getData().add(new XYChart.Data<>(i*ProcessedOutputData.xTimeMS, data.getSigma()));
            seriesFriction.getData().get(i).getNode().setVisible(false);
            seriesRollSpeed.getData().get(i).getNode().setVisible(false);
            seriesSigma.getData().get(i).getNode().setVisible(false);
        }
    }


    private void createGraph() {
        computeTimeLabel = new Label("Compute time : " + Orowan.lastComputeTime);

        xAxis.setLabel("Temps en seconde");
        yAxis.setAutoRanging(true);

        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setAnimated(false);

        seriesFriction = new XYChart.Series<Number, Number>();
        seriesFriction.setName("Friction");
        seriesRollSpeed = new XYChart.Series<Number, Number>();
        seriesRollSpeed.setName("Roll Speed");
        seriesSigma = new XYChart.Series<Number, Number>();
        seriesSigma.setName("Sigma");

        lineChart.getData().add(seriesRollSpeed);
        lineChart.getData().add(seriesSigma);
        lineChart.getData().add(seriesFriction);

        seriesFriction.getNode().setVisible(false);
        seriesRollSpeed.getNode().setVisible(false);
        seriesSigma.getNode().setVisible(false);
    }

    private void createButtons(VBox root) {
        menuButton = new Button("Menu");
        menuButton.setContentDisplay(ContentDisplay.LEFT);
        menuButton.setOnAction(event -> stage.setScene(new MenuView(new VBox(), stage)));

        checkBoxFriction = new CheckBox("Friction");
        checkBoxRollSpeed = new CheckBox("Roll Speed");
        checkBoxSigma = new CheckBox("Sigma");

        checkBoxFriction.setOnMouseClicked(event -> {
            seriesFriction.getNode().setVisible(checkBoxFriction.isSelected());
            for (XYChart.Data<Number, Number> data : seriesFriction.getData()) {
                Node node = data.getNode();
                node.setVisible(checkBoxFriction.isSelected());
            }
            reRangeYAxis(1);

        });
        checkBoxRollSpeed.setOnMouseClicked(event -> {
            seriesRollSpeed.getNode().setVisible(checkBoxRollSpeed.isSelected());
            for (XYChart.Data<Number, Number> data : seriesRollSpeed.getData()) {
                Node node = data.getNode();
                node.setVisible(checkBoxRollSpeed.isSelected());
            }
            reRangeYAxis(4);
        });
        checkBoxSigma.setOnMouseClicked(event -> {
            lineChart.getData().add(seriesSigma);
            seriesSigma.getNode().setVisible(checkBoxSigma.isSelected());
            for (XYChart.Data<Number, Number> data : seriesSigma.getData()) {
                Node node = data.getNode();
                node.setVisible(checkBoxSigma.isSelected());
            }
            reRangeYAxis(200);
        });
    }

    private void createScene(VBox root) {
        radioButtonRow.getChildren().add(checkBoxRollSpeed);
        radioButtonRow.getChildren().add(checkBoxFriction);
        radioButtonRow.getChildren().add(checkBoxSigma);
        hBoxComboBoxes.getChildren().add(standComboBox);
        hBoxComboBoxes.getChildren().add(stripIDComboBox);
        root.getChildren().add(titleLabel);
        root.getChildren().add(hBoxComboBoxes);
        root.getChildren().add(computeTimeLabel);
        root.getChildren().add(lineChart);
        root.getChildren().add(radioButtonRow);
        root.getChildren().add(menuButton);
    }

    private void reRangeYAxis(int max) {
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(max);
        yAxis.forceZeroInRangeProperty();
    }

    private void customComponents(VBox root) {
        menuButton.setGraphic(Assets.imageMap75.get("blackHome"));
        radioButtonRow.setStyle("-fx-alignment: center;" +
                "-fx-padding: 32px;");
        menuButton.setStyle("-fx-background-radius: 50;" +
                "-fx-pref-height: 50;" +
                "-fx-font-size: 16;");

        titleLabel.setGraphic(Assets.imageMap75.get("blackStats"));
        titleLabel.setStyle("-fx-font-size: 30px;" +
                "-fx-font-family: Times New Roman;");

        hBoxComboBoxes.setStyle("-fx-padding: 4px;");


        root.setStyle("-fx-alignment: center;" +
                "-fx-font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;");

    }

}
