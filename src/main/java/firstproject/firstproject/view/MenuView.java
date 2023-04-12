package firstproject.firstproject.view;

import firstproject.firstproject.assets.Assets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuView extends View {

    private Button viewGraphsButton = new Button("View Graphs");
    private Button manageUsersButton = new Button("Manage Users");
    private Button applicationSettingsButton = new Button("Application Settings");

    private HBox graphsBox = new HBox();
    private HBox usersBox = new HBox();
    private HBox settingsBox = new HBox();
    private HBox disconnectBox = new HBox();

    private Button disconnectButton = new Button("Disconnect");


    /**
     * Constructeur de la main view
     *
     * @param root         Noeud racine sur lequel se fait l'affichage
     * @param primaryStage PrimaryStage
     */
    public MenuView(VBox root, Stage primaryStage) {
        super(root, primaryStage);
        customComponents(root);

        setImage();
        createButton();
        createScene(root);

    }

    private void setImage() {
        viewGraphsButton.setGraphic(Assets.imageMap75.get("stats"));
        viewGraphsButton.setContentDisplay(ContentDisplay.LEFT);
        manageUsersButton.setGraphic(Assets.imageMap75.get("users"));
        manageUsersButton.setContentDisplay(ContentDisplay.LEFT);
        applicationSettingsButton.setGraphic(Assets.imageMap75.get("settings"));
        applicationSettingsButton.setContentDisplay(ContentDisplay.LEFT);

//        disconnectImage = new ImageView(currentDirectory + "/src/main/resources/firstproject/gui/door.png");
//        disconnectButton.setGraphic(disconnectImage);
        disconnectButton.setContentDisplay(ContentDisplay.LEFT);
    }

    private void createButton() {
        VBox root = new VBox();
        viewGraphsButton.setOnAction(e -> stage.setScene(new GraphView(root, stage)));
        manageUsersButton.setOnAction(e -> stage.setScene(new UserManagerView(root, stage)));
        applicationSettingsButton.setOnAction(e -> stage.setScene(new SettingsView(root, stage)));
        disconnectButton.setOnAction(e -> stage.setScene(new LoginView(root, stage)));
    }

    private void createScene(VBox root) {
        customComponents(root);

        // Add Regions to root children //
        root.getChildren().addAll(graphsBox,usersBox,settingsBox, disconnectBox);

        // Add Components to Regions children //
        graphsBox.getChildren().add(viewGraphsButton);
        usersBox.getChildren().add(manageUsersButton);
        settingsBox.getChildren().add(applicationSettingsButton);
        disconnectBox.getChildren().add(disconnectButton);

    }

    private void customComponents(VBox root) {
        graphsBox.setAlignment(Pos.CENTER);
        usersBox.setAlignment(Pos.CENTER);
        settingsBox.setAlignment(Pos.CENTER);
        disconnectBox.setAlignment(Pos.CENTER);

        graphsBox.prefWidthProperty().bind(root.widthProperty());
        usersBox.prefWidthProperty().bind(root.widthProperty());
        settingsBox.prefWidthProperty().bind(root.widthProperty());

        // Utiliser des contraintes de taille pour les boutons
        Button[] buttons = {viewGraphsButton, manageUsersButton, applicationSettingsButton, disconnectButton};
        for (Button button : buttons) {
            button.setMinWidth(150); // taille minimale
            button.setMaxWidth(Double.MAX_VALUE); // taille maximale
            button.setPrefWidth(300); // taille préférée
        }

        // Utiliser des propriétés de redimensionnement pour la VBox et les HBox
        root.setFillWidth(true);
        for (HBox hbox : new HBox[]{graphsBox, usersBox, settingsBox}) {
            hbox.setFillHeight(true);
            HBox.setHgrow(hbox, Priority.ALWAYS);
        }


        root.setStyle("-fx-alignment: center");
    }


}
