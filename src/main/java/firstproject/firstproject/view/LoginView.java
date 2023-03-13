package firstproject.firstproject.view;

import firstproject.firstproject.controller.Controller;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView extends View {

    private Controller controller;

    private Label titleLabel = new Label("FRIST Project");

    private HBox identifierBox = new HBox();
    private Label identifierLabel = new Label("Identifiant : ");
    private TextField identifierTextField = new TextField();

    private HBox passwordBox = new HBox();
    private Label passwordLabel = new Label("Mot de passe : ");
    private PasswordField passwordTextField = new PasswordField();

    private Button connectionButton = new Button("Connection");

    /**
     * Constructeur de la main view
     *
     * @param root         Noeud racine sur lequel se fait l'affichage
     * @param primaryStage PrimaryStage
     * @param controller   Controleur associé à la view
     */
    public LoginView(VBox root, Stage primaryStage, Controller controller) {
        super(root, primaryStage, controller);
        this.controller = controller;
        createButton();
        createScene(root);

    }

    private void createButton() {
        VBox root = new VBox();
        connectionButton.setOnAction(e -> stage.setScene(new GraphView(root, stage, controller)));
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
        root.getChildren().add(titleLabel);
        root.getChildren().add(identifierBox);
        root.getChildren().add(passwordBox);
        root.getChildren().add(connectionButton);

        // Add Components to Regions children //
        identifierBox.getChildren().add(identifierLabel);
        identifierBox.getChildren().add(identifierTextField);
        passwordBox.getChildren().add(passwordLabel);
        passwordBox.getChildren().add(passwordTextField);
    }

    private void customComponents(VBox root) {
        root.setStyle("-fx-alignment: center");
        identifierBox.setStyle("-fx-alignment: center");
        passwordBox.setStyle("-fx-alignment: center");
    }


}
