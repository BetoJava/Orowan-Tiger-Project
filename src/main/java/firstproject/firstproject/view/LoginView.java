package firstproject.firstproject.view;

import firstproject.firstproject.Main;
import firstproject.firstproject.controller.H2Database;
import firstproject.firstproject.model.User;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginView extends View {

    private Label titleLabel = new Label("OROWAN");

    private VBox fieldBox = new VBox();

    private HBox identifierBox = new HBox();
    private Label identifierLabel = new Label("User ID : ");
    private TextField identifierTextField = new TextField();

    private HBox passwordBox = new HBox();
    private Label passwordLabel = new Label("Password : ");
    private PasswordField passwordTextField = new PasswordField();

    private Button connectionButton = new Button("Login");

    /**
     * Constructeur de la main view
     *
     * @param root         Noeud racine sur lequel se fait l'affichage
     * @param primaryStage PrimaryStage
     */
    public LoginView(VBox root, Stage primaryStage) {
        super(root, primaryStage);
        customComponents(root);
        createButton();
        createScene(root);

    }

    private void createButton() {
        connectionButton.setOnAction(e -> {
            H2Database h2 = H2Database.getInstance();
            // If user exist, change view to MenuView
            if(h2.loginUser(identifierTextField.getText(), passwordTextField.getText())) {
                Main.setCurrentUser(new User(0, identifierTextField.getText(), passwordTextField.getText()));
                stage.setScene(new MenuView(new VBox(), stage));
            }
        });
    }

    private void createScene(VBox root) {
        customComponents(root);

        // Add Regions to root children //
        root.getChildren().add(titleLabel);
        root.getChildren().add(fieldBox);
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
        titleLabel.setStyle("-fx-font-size: 60px;" +
                "-fx-font-family: Times New Roman;");
    }


}
