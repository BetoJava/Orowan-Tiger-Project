package firstproject.firstproject.view;

import firstproject.firstproject.controller.Controller;
import javafx.scene.control.Button;

import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuView extends View {

    private Controller controller;

    private Button viewGraphs = new Button("View Graphs");
    private Button manageUsers = new Button("Manage Users");
    private Button applicationSettingsButton = new Button("Application Settings");
    private ImageView settingsImage;
    private Button disconnect = new Button("Disconnect");



    /**
     * Constructeur de la main view
     *
     * @param root         Noeud racine sur lequel se fait l'affichage
     * @param primaryStage PrimaryStage
     * @param controller   Controleur associé à la view
     */
    public MenuView(VBox root, Stage primaryStage, Controller controller) {
        super(root, primaryStage, controller);
        this.controller = controller;
        setImage();
        createButton();
        createScene(root);


    }

    private void setImage() {
        settingsImage = new ImageView("./src/main/firstproject/gui/settings.png");
        applicationSettingsButton.setGraphic(settingsImage);
        applicationSettingsButton.setContentDisplay(ContentDisplay.LEFT);
    }

    private void createButton() {
        VBox root = new VBox();
        //connectionButton.setOnAction(e -> stage.setScene(new GraphView(root, stage, controller)));
        // La même chose peut être faite de cette manière :
        /*
        connectionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setScene(new GraphView(root, stage, controller));
            }
        });
         */

    }

    private void createScene(VBox root) {
        customComponents(root);

        // Add Regions to root children //
        root.getChildren().add(applicationSettingsButton);

        // Add Components to Regions children //
    }

    private void customComponents(VBox root) {
        root.setStyle("-fx-alignment: center");
    }


}
