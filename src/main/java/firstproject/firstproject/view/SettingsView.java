package firstproject.firstproject.view;

import firstproject.firstproject.controller.Controller;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class SettingsView extends View{
    /**
     * Constructeur des différentes View
     *
     * @param root         Noeud racine sur lequel se fait l'affichage
     * @param primaryStage PrimaryStage
     * @param controller   Controleur associé à la view
     */


    private ImageView settingsImage;
    private Label titleLabel = new Label("Application Settings");

    private Label standLabel = new Label("Stand ID: ");
    private HBox titleBox = new HBox();
    private HBox standBox = new HBox();

    private ToggleButton toggleButton = new ToggleButton();

    private SwitchButton switchButton = new SwitchButton();
    ComboBox<String> comboBox = new ComboBox<>();



    public SettingsView(VBox root, Stage primaryStage, Controller controller) {
        super(root, primaryStage, controller);
        setImage();
        createScene(root);

    }

    private void setImage() {
        String currentDirectory = System.getProperty("user.dir");
        settingsImage = new ImageView(currentDirectory + "/src/main/resources/firstproject/gui/settings.png");
        titleLabel.setGraphic(settingsImage);
    }


    private void createScene(VBox root) {
        customComponents(root);
        // ComboBox
        comboBox.getItems().addAll("Stand 1", "Stand 2", "Stand 3");
        String choixSelectionne = comboBox.getValue();

        //Toggle Button

        toggleButton.getStyleClass().add("toggle-button");

        toggleButton.setSelected(true); // pour le mettre dans l'état "activé"

        toggleButton.setOnAction(e -> {
            if (toggleButton.isSelected()) {
                // Le switch est activé
                toggleButton.setText("Enable");
                System.out.println("Switch activé");
            } else {
                // Le switch est désactivé
                toggleButton.setText("Disable");
                System.out.println("Switch désactivé");
            }
        });

        // Add Regions to root children //
        root.getChildren().addAll(titleBox,standBox,toggleButton,switchButton);

        // Add Components to Regions children //
        titleBox.getChildren().add(titleLabel);
        standBox.getChildren().addAll(standLabel,comboBox);


    }
    private void customComponents(VBox root) {
        root.setStyle("-fx-alignment: center");
        titleBox.setStyle("-fx-alignment: center");
        standBox.setStyle("-fx-alignment: center");


    }
}
