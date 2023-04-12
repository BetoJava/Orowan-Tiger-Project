package firstproject.firstproject.view;

import firstproject.firstproject.controller.Controller;
import firstproject.firstproject.model.Orowan;
import firstproject.firstproject.model.OrowanOutputData;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GraphView extends View {

    private HBox radioButtonRow = new HBox();

    private ComboBox<String> comboBox;
    private LineChart<Number, Number> lineChart;
    private XYChart.Series<Number, Number> seriesFriction;
    private XYChart.Series<Number, Number> seriesSigma;
    private XYChart.Series<Number, Number> seriesRollSpeed;
    private XYChart.Series<Number, Number> cheating;
    private ArrayList<OrowanOutputData> outputData;
    private Label computeTimeLabel;
    private boolean flagRollSpeed;
    private boolean flagSigma;
    private boolean flagFriction;
    private CheckBox checkBoxRollSpeed;
    private CheckBox checkBoxFriction;
    private CheckBox checkBoxSigma;

    /**
     * Constructeur de la View graphique
     *
     * @param root         Noeud racine sur lequel se fait l'affichage
     * @param primaryStage PrimaryStage
     * @param controller   Controleur associé à la view
     */
    public GraphView(VBox root, Stage primaryStage, Controller controller) {
        super(root, primaryStage, controller);

        outputData = Orowan.computeOrowanModel("1939351","F2");
        createButtons();
        createGraph();
        addDataToGraph(outputData);
        createScene(root);
    }

    private void addDataToGraph(ArrayList<OrowanOutputData> outputData) {
        for(int i = 0; i < outputData.size(); i++) {
            OrowanOutputData data = outputData.get(i);
//            seriesFriction.getData().add(new XYChart.Data<>(i*OrowanOutputData.xTimeMS, data.getFriction()));
//            seriesRollSpeed.getData().add(new XYChart.Data<>(i*OrowanOutputData.xTimeMS, data.getFriction()));
//            seriesSigma.getData().add(new XYChart.Data<>(i*OrowanOutputData.xTimeMS, data.getFriction()));
        }
    }


    private void createGraph() {
        computeTimeLabel = new Label("Compute time : " + Orowan.lastComputeTime);

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Temps en seconde");

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
        lineChart.setTitle(yAxis.getLabel() + " in function of " + xAxis.getLabel());
    }

    private void createButtons() {
        checkBoxRollSpeed = new CheckBox("Roll Speed");
        checkBoxFriction = new CheckBox("Friction");
        checkBoxSigma = new CheckBox("Sigma");

        //checkBoxRollSpeed.setOnMouseClicked();
        //checkBoxFriction.setOnMouseClicked();
        //checkBoxSigma.setOnMouseClicked();
    }

    private void createScene(VBox root) {
        radioButtonRow.getChildren().add(checkBoxRollSpeed);
        radioButtonRow.getChildren().add(checkBoxFriction);
        radioButtonRow.getChildren().add(checkBoxSigma);
        root.getChildren().add(computeTimeLabel);
        root.getChildren().add(lineChart);
        root.getChildren().add(radioButtonRow);
    }


}
