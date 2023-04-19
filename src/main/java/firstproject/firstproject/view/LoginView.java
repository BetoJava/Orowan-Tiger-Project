package firstproject.firstproject.view;

import firstproject.firstproject.controller.H2Database;
import firstproject.firstproject.assets.Assets;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginView extends View {



    private final Label titleLabel = new Label("OROWAN");
    private final Label errorLabel =  new Label ("");
    private final VBox fieldBox = new VBox();

    private final HBox identifierBox = new HBox();
    private final Label identifierLabel = new Label("Username : ");
    private final TextField identifierTextField = new TextField();

    private final HBox passwordBox = new HBox();
    private final Label passwordLabel = new Label("Password : ");
    private final PasswordField passwordTextField = new PasswordField();

    private final Button connectionButton = new Button("Login");

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

    /**
     * Crée et configure les boutons de la view.
     */
    private void createButton() {
        connectionButton.setOnAction(e -> {
            H2Database h2 = H2Database.getInstance();
            // If user exist, change view to MenuView
            if(h2.loginUser(identifierTextField.getText(), passwordTextField.getText())) {
                stage.setScene(new MenuView(new VBox(), stage));
            }
            else {
                errorLabel.setText("Wrong username or password.");
            }
        });
    }

    /**
     * Crée la scène en y ajoutant tous les éléments à leur parent.
     */
    private void createScene(VBox root) {
        customComponents(root);

        // Add Regions to root children //
        root.getChildren().add(titleLabel);
        root.getChildren().add(errorLabel);
        root.getChildren().add(fieldBox);
        root.getChildren().add(connectionButton);

        // Add Components to Regions children //
        fieldBox.getChildren().addAll(identifierBox,passwordBox);
        identifierBox.getChildren().add(identifierLabel);
        identifierBox.getChildren().add(identifierTextField);
        passwordBox.getChildren().add(passwordLabel);
        passwordBox.getChildren().add(passwordTextField);

        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    connectionButton.fire();
                }
            }
        });
    }

    /**
     * Applique du style aux différents conmposants.
     */
    private void customComponents(VBox root) {

        identifierBox.setStyle("-fx-alignment: center;" +
                "-fx-padding: 4px;");
        passwordBox.setStyle("-fx-alignment: center;" +
                "-fx-padding: 4px;");
        fieldBox.prefWidthProperty().bind(passwordBox.prefWidthProperty());
        fieldBox.setStyle("-fx-alignment: center;");
        fieldBox.setSpacing(4);

        root.setStyle("-fx-alignment: center;" +
                "-fx-font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;");


        // Créer un objet BackgroundImage avec l'image de fond
        BackgroundImage background = new BackgroundImage(Assets.backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        // Appliquer l'arrière-plan à la VBox
        root.setBackground(new Background(background));
        root.setSpacing(10);


        // Changer la couleur et la police d'écriture du titleLabel
        titleLabel.setTextFill(Color.ORANGE);
        titleLabel.setStyle("-fx-font-size: 60px;" +
                "-fx-font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;" +
                "-fx-alignment: center");

        // Set text color
        errorLabel.setStyle("-fx-text-fill: red;"+"-fx-alignment: center");
        identifierLabel.setStyle("-fx-text-fill: white;");
        passwordLabel.setStyle("-fx-text-fill: white;");

        // Set button color
        connectionButton.setStyle("-fx-background-color: #2f2f2f;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 30;" +
                "-fx-alignment: center;" +
                "-fx-font-size: 16px");

        // Set text field color
        identifierTextField.setStyle("-fx-background-color: #383838;" +
                "-fx-text-fill: white;");
        passwordTextField.setStyle("-fx-background-color: #383838;" +
                "-fx-text-fill: white;");
    }


}
