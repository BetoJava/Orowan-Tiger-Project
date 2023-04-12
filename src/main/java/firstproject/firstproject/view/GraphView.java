package firstproject.firstproject.view;

import firstproject.firstproject.assets.Assets;
import firstproject.firstproject.model.Orowan;
import firstproject.firstproject.model.OrowanOutputData;
import firstproject.firstproject.model.ProcessedOutputData;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    private ArrayList<OrowanOutputData> outputData;
    private Label computeTimeLabel;
    private CheckBox checkBoxRollSpeed;
    private CheckBox checkBoxFriction;
    private CheckBox checkBoxSigma;
    private Button menuButton;

    /**
     * Constructeur de la View graphique
     *
     * @param root         Noeud racine sur lequel se fait l'affichage
     * @param primaryStage PrimaryStage
     */
    public GraphView(VBox root, Stage primaryStage) {
        super(root, primaryStage);

        outputData = Orowan.computeOrowanModel("1939351","F2");
        ArrayList<ProcessedOutputData> processedOutputData = Orowan.getProcessedOutputData(outputData, "1939351","F2");

        createButtons(root);
        createGraph();
        addDataToGraph(processedOutputData);
        createScene(root);
        customComponents(root);
    }

    private void addDataToGraph(ArrayList<ProcessedOutputData> outputData) {
        for(int i = 0; i < outputData.size(); i++) {
            ProcessedOutputData data = outputData.get(i);
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
        root.getChildren().add(titleLabel);
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
        menuButton.setGraphic(Assets.imageMap75.get("home"));
        root.setStyle("-fx-alignment: center");
        radioButtonRow.setStyle("-fx-alignment: center");
        titleLabel.setGraphic(Assets.imageMap75.get("stats"));
        titleLabel.setStyle("-fx-font-size: 30px;" +
                "-fx-font-family: Times New Roman;");
    }

}
