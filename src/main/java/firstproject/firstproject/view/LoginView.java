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

    private GridPane gp = new GridPane();

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
                int id = 1;
                for(User u : h2.getUsers()) {
                    if(u.getIdentifier() == identifierTextField.getText() && u.getPassword() == passwordTextField.getText()) {
                        id = u.getId();
                        break;
                    }
                }
                Main.setCurrentUser(new User(id, identifierTextField.getText(), passwordTextField.getText()));
                Main.getCurrentUser().setStandList(h2.getUserStands(id));
                stage.setScene(new MenuView(new VBox(), stage));
                System.out.println(Main.getCurrentUser().getStandList());
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
        fieldBox.getChildren().addAll(identifierBox,passwordBox);
        identifierBox.getChildren().add(identifierLabel);
        identifierBox.getChildren().add(identifierTextField);
        passwordBox.getChildren().add(passwordLabel);
        passwordBox.getChildren().add(passwordTextField);
    }

    private void customComponents(VBox root) {

        identifierBox.setStyle("-fx-alignment: center;" +
                "-fx-padding: 4px;");
        passwordBox.setStyle("-fx-alignment: center;" +
                "-fx-padding: 4px;");
        fieldBox.prefWidthProperty().bind(passwordBox.prefWidthProperty());
        fieldBox.setStyle("-fx-alignment: center;");
        fieldBox.setSpacing(4);

        root.setStyle("-fx-alignment: center;" +
                "-fx-font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;" +
                "-fx-background-color: #222222;");
        root.setSpacing(10);


        // Changer la couleur et la police d'écriture du titleLabel
        titleLabel.setTextFill(Color.ORANGE);
        titleLabel.setStyle("-fx-font-size: 60px;" +
                "-fx-font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;" +
                "-fx-alignment: center");

        // Set text color
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
