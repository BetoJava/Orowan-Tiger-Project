package firstproject.firstproject.view;

import firstproject.firstproject.assets.Assets;
import firstproject.firstproject.controller.H2Database;
import firstproject.firstproject.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class UserManagerView extends View {


    private Label titleLabel = new Label("Manage Users");

    private HBox titleBox = new HBox();
    private HBox addBox = new HBox();
    private VBox tableBox = new VBox();
    private Button menuButton = new Button("Menu");
    private Button addUserButton = new Button("ADD USER");

    private ArrayList<User> usersList = new ArrayList<>();


    private TableView<User> table = new TableView<>();

    /**
     * Constructeur de la View graphique
     * @param root         Noeud racine sur lequel se fait l'affichage
     * @param primaryStage PrimaryStage
     */
    public UserManagerView(VBox root, Stage primaryStage) {
        super(root, primaryStage);

        usersList = H2Database.getInstance().getUsers();

        customComponents(root);
        createScene(root);
    }

    private void createScene(VBox root) {

        TableColumn<User, String> columnName = new TableColumn<>("Username");
        TableColumn<User, String> columnRole = new TableColumn<>("Role");
        table.getColumns().addAll(columnName, columnRole);
        columnName.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        columnRole.setCellValueFactory(new PropertyValueFactory<>("role"));


        ObservableList<User> users = FXCollections.observableArrayList();
        users.addAll(usersList);
        table.setItems(users);


        // Selection d'un utilisateur et modifications de ses propriétés
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedUser) -> {
            if (selectedUser != null) {
                createModifyingUserView(selectedUser);
            }
        });

        // Ajout d'un utilisateur
        addUserButton.setOnAction(event -> {
            createAddUserView(users);
        });

        menuButton.setOnAction(e -> stage.setScene(new MenuView(new VBox(), stage)));

        tableBox.getChildren().add(table);
        titleBox.getChildren().add(titleLabel);
        root.getChildren().addAll(titleBox, addUserButton, tableBox, menuButton);

    }

    private void createAddUserView(ObservableList<User> users) {
        Stage stage = new Stage();
        VBox newRoot = new VBox();
        Scene scene = new Scene(newRoot, 300, 200);
        scene.setOnMouseClicked(e -> stage.requestFocus());

        Label label = new Label("Add new User");
        label.setGraphic(Assets.imageMap75.get("blackPersonAdd"));
        Label errorLabel = new Label("");
        HBox usernameBox = new HBox();
        HBox passwordBox = new HBox();
        Label usernameLabel = new Label ("User Id :");
        Label passwordLabel = new Label ("Password :");
        TextField usernameTextField = new TextField();
        TextField passwordTextField = new TextField();
        usernameBox.getChildren().addAll(usernameLabel,usernameTextField);
        passwordBox.getChildren().addAll(passwordLabel,passwordTextField);
        Button saveButton = new Button("Save");

        // Créer deux boutons de bascule pour les rôles "Admin" et "User"
        ToggleButton adminToggleButton = new ToggleButton(User.ENGINEER);
        ToggleButton userToggleButton = new ToggleButton(User.WORKER);

        // Créer un groupe pour les boutons de bascule
        ToggleGroup toggleGroup = new ToggleGroup();
        adminToggleButton.setToggleGroup(toggleGroup);
        userToggleButton.setToggleGroup(toggleGroup);

        // Créer une HBox pour contenir les boutons de bascule
        HBox toggleButtonsBox = new HBox();
        toggleButtonsBox.getChildren().addAll(adminToggleButton, userToggleButton);

        // Appliquer le style
        //newRoot.setStyle(oggleButtonsBox.getChildren().addAll(adminToggleButton, userToggleButton);

        // Créer un événement pour le bouton "Save"
        saveButton.setOnAction(e -> {
            // Récupérer les valeurs saisies dans les champs de texte
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            ToggleButton selectedToggle = (ToggleButton)toggleGroup.getSelectedToggle();
            String role = selectedToggle.getText(); // Récupérer le rôle sélectionné

            if (username.isEmpty() || password.isEmpty() || role == null) {
                errorLabel.setText("Please fill all fields.");
            } else {

            // Ajouter un nouvel utilisateur à la liste existante
            User newUser = new User(0, username, password, role);
            users.add(newUser);
            H2Database.getInstance().addUser(newUser.getIdentifier(), newUser.getPassword(), newUser.isEngineer());

            // Fermer la fenêtre d'ajout d'utilisateur
            stage.close();

            }


        });

        stage.setTitle("Adding new user");

        newRoot.setStyle("-fx-alignment: center;");
        usernameBox.setStyle("-fx-alignment: center;" +
                "-fx-padding: 4px 0px;");
        passwordBox.setStyle("-fx-alignment: center;" +
                "-fx-padding: 4px 0px;");
        toggleButtonsBox.setStyle("-fx-alignment: center;" +
                "-fx-padding: 4px 0px;");
        saveButton.setStyle("-fx-padding: 4px;");
        label.setStyle("-fx-padding: 4px;" +
                "-fx-font-size: 22px;");
        errorLabel.setStyle("-fx-padding: 4px;"+"-fx-text-fill : red;");

        // Ajouter tous les éléments à la nouvelle scene
        newRoot.getChildren().addAll(label,errorLabel, usernameBox, passwordBox,toggleButtonsBox,saveButton);
        // Afficher la nouvelle fenêtre
        stage.setScene(scene);
        stage.show();
    }

    private void createModifyingUserView(User selectedUser) {
        String userIdentifier = selectedUser.getIdentifier();
        String password = selectedUser.getPassword();
        String role = selectedUser.getRole();

        Label labelUserName = new Label("Username : " + userIdentifier);
        Label labelPassword = new Label("Password : " + password);
        Label labelNewUsername = new Label("New Username : ");
        Label labelNewPassword = new Label("New Password : ");
        Label errorLabel = new Label("");
        TextField newUsernameTV = new TextField();
        TextField newPasswordTV = new TextField();
        TextField confirmPasswordTV = new TextField();
        confirmPasswordTV.setPromptText("Confirm Password");
        HBox newUsernameBox = new HBox();
        HBox newPasswordBox = new HBox();
        HBox buttonBox = new HBox();
        newUsernameBox.getChildren().addAll(labelNewUsername,newUsernameTV);
        newPasswordBox.getChildren().addAll(labelNewPassword,newPasswordTV);
        Button saveButton = new Button("Save");
        Button deleteButton = new Button("Delete");
        buttonBox.getChildren().addAll(saveButton, deleteButton);

        // Créer deux boutons de bascule pour les rôles "Admin" et "User"
        ToggleButton adminToggleButton = new ToggleButton(User.ENGINEER);
        ToggleButton userToggleButton = new ToggleButton(User.WORKER);

        // Créer un groupe pour les boutons de bascule
        ToggleGroup toggleGroup = new ToggleGroup();
        adminToggleButton.setToggleGroup(toggleGroup);
        userToggleButton.setToggleGroup(toggleGroup);

        // Créer une HBox pour contenir les boutons de bascule
        HBox toggleButtonsBox = new HBox();
        toggleButtonsBox.getChildren().addAll(adminToggleButton, userToggleButton);

        // Parcourir les boutons de bascule pour trouver celui qui correspond au rôle de l'utilisateur
        ToggleButton selectedToggleButton = null;
        for (Toggle toggle : toggleGroup.getToggles()) {
            ToggleButton toggleButton = (ToggleButton) toggle;
            if (toggleButton.getText().equals(role)) {
                selectedToggleButton = toggleButton;
                break;
            }
        }

        // Sélectionner le bouton de bascule correspondant par défaut
        if (selectedToggleButton != null) {
            selectedToggleButton.setSelected(true);
        }

        VBox vbox = new VBox(labelUserName, labelPassword,errorLabel, newUsernameBox, newPasswordBox,confirmPasswordTV, toggleButtonsBox, buttonBox);

        Scene scene = new Scene(vbox, 300, 200);

        Stage newStage = new Stage();
        newStage.setTitle("Modifying " + userIdentifier);

        vbox.setStyle("-fx-alignment: center;"+
                "-fx-font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif;"+
                "-fx-background-color:#222222");
        newUsernameBox.setStyle("-fx-alignment: center;" +
                "-fx-padding: 4px 0px;");
        newPasswordBox.setStyle("-fx-alignment: center;" +
                "-fx-padding: 4px 0px;");
        toggleButtonsBox.setStyle("-fx-alignment: center;" +
                "-fx-padding: 4px;");
        buttonBox.setStyle("-fx-padding: 4px;");
        saveButton.setStyle("-fx-background-color: #3fa168;" +
                "-fx-text-fill: white;");
        deleteButton.setStyle("-fx-background-color: #E72A2A;" +
                "-fx-text-fill: white;");
        labelUserName.setStyle("-fx-padding: 4px;" +
                "-fx-font-weight: bold;"+"-fx-text-fill: white;");
        labelPassword.setStyle("-fx-padding: 4px;" +
                "-fx-font-weight: bold;"+"-fx-text-fill: white;");
        errorLabel.setStyle("-fx-padding : 4px;"+"-fx-text-fill : red;");
        labelNewUsername.setStyle("-fx-padding: 4px;"+"-fx-text-fill:white;");
        labelNewPassword.setStyle("-fx-padding: 4px;"+ "-fx-text-fill: white;");
        newUsernameTV.setStyle("-fx-background-color: #383838;" +
                "-fx-text-fill: white;");
        newPasswordTV.setStyle("-fx-background-color: #383838;" + "-fx-text-fill: white;");
        confirmPasswordTV.setStyle("-fx-background-color:#383838 ;"+ "-fx-prompt-text-fill: gray;" + "-fx-text-fill: white;");
        newStage.setScene(scene);
        newStage.show();

        // Créer un événement pour le bouton "Save"

        saveButton.setOnAction(event -> {
            String newUsername = newUsernameTV.getText();
            String newPassword = newPasswordTV.getText();
            ToggleButton selectedToggle = (ToggleButton)toggleGroup.getSelectedToggle();
            String newRole = selectedToggle.getText();

            if (newUsername.isEmpty() || newPassword.isEmpty() || newRole == null) {
                errorLabel.setText("Please fill all fields.");
            } else if (!newPassword.equals(confirmPasswordTV.getText())) {
                errorLabel.setText("Passwords don't match");
            } else {

            // Mettre à jour les propriétés de l'utilisateur sélectionné
                    selectedUser.setIdentifier(newUsernameTV.getText());
                    selectedUser.setPassword(newPasswordTV.getText());
                    selectedUser.setRole(newRole);

                H2Database.getInstance().updateUser(selectedUser.getId(), selectedUser.getIdentifier(),
                                                selectedUser.getPassword(), selectedUser.isEngineer());
                // Fermer la fenêtre d'ajout d'utilisateur
                newStage.close();
                table.getSelectionModel().clearSelection();
                table.refresh();


            }
        });

        deleteButton.setOnAction(event -> {
            H2Database.getInstance().removeUser(selectedUser.getId());
            usersList.remove(selectedUser);
            table.getItems().remove(selectedUser);

            // Fermer la fenêtre d'ajout d'utilisateur
            newStage.close();
            table.getSelectionModel().clearSelection();
            table.refresh();
        });
    }

    private void customComponents(VBox root) {
        titleLabel.setGraphic(Assets.imageMap75.get("users"));
        addUserButton.setGraphic(Assets.imageMap75.get("personAdd"));
        menuButton.setGraphic(Assets.imageMap75.get("home"));
        menuButton.setContentDisplay(ContentDisplay.LEFT);
        menuButton.setStyle("-fx-background-color: #2f2f2f;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 50;" +
                "-fx-pref-height: 50;" +
                "-fx-font-size: 16;" +
                "-fx-padding: 0px 16px;");

        addUserButton.setStyle("-fx-background-color: #2f2f2f;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 50;" +
                "-fx-pref-height: 50;" +
                "-fx-font-size: 16;" +
                "-fx-padding: 0px 16px;");

        table.setStyle("-fx-background-color: #333333; " +
                "-fx-text-background-color: white; " +
                "-fx-control-inner-background: #333333;" +
                "-fx-table-header-background-color: #333333;" +
                "-fx-text-fill: white;" +
                "-fx-selection-bar: #555555;" +
                "-fx-selection-bar-text: white;" +
                "-fx-table-cell-border-color: white; " +
                "-fx-table-header-border-color: white;");


        tableBox.setStyle("-fx-padding: 16px 400px;");

        root.setStyle("-fx-alignment: center;" +
                "-fx-font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;" +
                "-fx-background-color: #222222;");

        titleBox.setStyle("-fx-alignment: center");
        titleLabel.setStyle("-fx-font-size: 30px;" +
                "-fx-font-family: Times New Roman;" +
                "-fx-text-fill: white;" +
                "-fx-padding: 16px;");


    }
}

