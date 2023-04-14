package firstproject.firstproject.view;

import firstproject.firstproject.assets.Assets;
import firstproject.firstproject.controller.H2Database;
import firstproject.firstproject.model.Stand;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class SettingsView extends View{

    private final Label titleLabel = new Label("Application Settings");

    private final Label standLabel = new Label("Stand ID: ");

    private final HBox standBox = new HBox();
    private final Button menuButton = new Button("Menu");
    private Label labelUserName;


    private final ComboBox<String> comboBox = new ComboBox<>();


    /**
     * Constructeur des différentes View
     *
     * @param root         Noeud racine sur lequel se fait l'affichage
     * @param primaryStage PrimaryStage
     */
    public SettingsView(VBox root, Stage primaryStage) {
        super(root, primaryStage);
        customComponents(root);
        createButton();
        createScene(root);

    }


    private void createButton(){
        menuButton.setOnAction(e -> stage.setScene(new MenuView(new VBox(), stage)));
    }

    private void createScene(VBox root) {

        // ComboBox

        for(Stand s : H2Database.getUserStands()) {
            comboBox.getItems().add("F" + s.getStandID());
        }

        // Action Toggle button
        ToggleButton toggleButton = new ToggleButton("Disable");
        toggleButton.setStyle("-fx-background-color: #383838; -fx-text-fill: white;");

        toggleButton.setOnAction(event -> {
            String standID = String.valueOf(comboBox.getValue().charAt(1));
            standID = String.valueOf(Integer.parseInt(standID));
            if(toggleButton.isSelected()) {
                toggleButton.setText("Enable");
                toggleButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                getStandFromID(standID).setEnable(true);
                H2Database.getInstance().enableStand(standID);
            } else {
                toggleButton.setText("Disable");
                toggleButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                getStandFromID(standID).setEnable(false);
                H2Database.getInstance().disableStand(standID);
            }
        });

        // Action comboBox
        comboBox.setOnAction(event -> {
            String standID = String.valueOf(comboBox.getValue().charAt(1));
            standID = String.valueOf(Integer.parseInt(standID));
            Stand selectedStand = getStandFromID(standID);
            toggleButton.setSelected(selectedStand.isEnable());
            toggleButton.setText(selectedStand.isEnable() ? "Enable" : "Disable");
            if (toggleButton.getText().equals("Enable")){
                toggleButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            } else {
                toggleButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            }
            H2Database.getInstance().updateStand(standID, selectedStand.isEnable());
        });



        standBox.getChildren().addAll(standLabel, comboBox, toggleButton);
        root.getChildren().addAll(titleLabel, labelUserName, standBox, menuButton);


    }
    private void customComponents(VBox root) {
        labelUserName = new Label("User : " + H2Database.getUserIdentifier());
        labelUserName.setStyle("-fx-font-weight: bold;"+"-fx-text-fill: white;" +
                "-fx-font-style: italic;");

        titleLabel.setGraphic(Assets.imageMap75.get("settings"));
        menuButton.setGraphic(Assets.imageMap75.get("home"));
        menuButton.setContentDisplay(ContentDisplay.LEFT);
        menuButton.setStyle("-fx-background-color: #2f2f2f;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 50;" +
                "-fx-pref-height: 50;" +
                "-fx-font-size: 16;" +
                "-fx-padding: 0px 16px;");

        comboBox.setStyle("-fx-border-color: gray;");
        standBox.setStyle("-fx-alignment: center");
        standBox.setSpacing(10);
        titleLabel.setStyle("-fx-font-size: 30px;" +
                "-fx-font-family: Times New Roman;" +
                "-fx-text-fill: white;" +
                "-fx-padding: 48px;");
        standLabel.setStyle("-fx-text-fill: white;");

        root.setStyle("-fx-alignment: center;" +
                "-fx-font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;" +
                "-fx-background-color: #222222;");
        root.setSpacing(10);



    }

    private Stand getStandFromID(String standID) {
        for(Stand s : H2Database.getUserStands()) {
            if(Integer.valueOf(s.getStandID()).equals(Integer.valueOf(standID))) {
                return s;
            }
        }
        return null;
    }
}
