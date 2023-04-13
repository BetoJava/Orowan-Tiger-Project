package firstproject.firstproject.view;

import firstproject.firstproject.Main;
import firstproject.firstproject.assets.Assets;
import firstproject.firstproject.model.Stand;
import firstproject.firstproject.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class SettingsView extends View{

    private ImageView settingsImage;
    private Label titleLabel = new Label("Application Settings");

    private Label standLabel = new Label("Stand ID: ");

    private HBox standBox = new HBox();
    private Button menuButton = new Button("Menu");


    private ComboBox<String> comboBox = new ComboBox<>();


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

        for(Stand s : Main.getCurrentUser().getStandList()) {
            comboBox.getItems().add(s.getStandID());
        }

        // Action Toggle button
        ToggleButton toggleButton = new ToggleButton("Disable");
        toggleButton.setStyle("-fx-background-color: #383838; -fx-text-fill: white;");

        toggleButton.setOnAction(event -> {
            if(toggleButton.isSelected()) {
                toggleButton.setText("Enable");
                toggleButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
                getStandFromID(comboBox.getValue()).setEnable(true);
            } else {
                toggleButton.setText("Disable");
                toggleButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                getStandFromID(comboBox.getValue()).setEnable(false);
            }
        });

        // Action comboBox
        comboBox.setOnAction(event -> {
            Stand selectedStand = getStandFromID(comboBox.getValue());
            toggleButton.setSelected(selectedStand.isEnable());
            toggleButton.setText(selectedStand.isEnable() ? "Enable" : "Disable");
        });



        standBox.getChildren().addAll(standLabel, comboBox, toggleButton);
        root.getChildren().addAll(titleLabel, standBox, menuButton);


    }
    private void customComponents(VBox root) {

        titleLabel.setGraphic(Assets.imageMap75.get("settings"));
        menuButton.setGraphic(Assets.imageMap75.get("home"));
        menuButton.setContentDisplay(ContentDisplay.LEFT);
        menuButton.setStyle("-fx-background-color: #2f2f2f;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 50;" +
                "-fx-pref-height: 50;" +
                "-fx-font-size: 16;");

        comboBox.setStyle("-fx-background-color: #222222;" +
                " -fx-text-fill: white;" +
                "-fx-padding: 32px;");
        standBox.setStyle("-fx-alignment: center");
        titleLabel.setStyle("-fx-font-size: 60px;" +
                "-fx-font-family: Times New Roman;" +
                "-fx-text-fill: white;" +
                "-fx-padding: 48px;");

        root.setStyle("-fx-alignment: center;" +
                "-fx-font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;" +
                "-fx-background-color: #222222;");




    }

    private Stand getStandFromID(String standID) {
        for(Stand s : Main.getCurrentUser().getStandList()) {
            if(s.getStandID() == standID) {
                return s;
            }
        }
        return null;
    }
}
