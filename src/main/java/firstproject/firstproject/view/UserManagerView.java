package firstproject.firstproject.view;

import firstproject.firstproject.assets.Assets;
import firstproject.firstproject.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserManagerView extends View {


    private Label titleLabel = new Label("Manage Users");

    private HBox titleBox = new HBox();
    private HBox addBox = new HBox();
    private VBox tableBox = new VBox();
    private Button menuButton = new Button("Menu");
    private Button addUserButton = new Button("ADD USER");


    private TableView<User> table = new TableView<>();


    /**
     * Constructeur de la View graphique
     *
     * @param root         Noeud racine sur lequel se fait l'affichage
     * @param primaryStage PrimaryStage
     */
    public UserManagerView(VBox root, Stage primaryStage) {
        super(root, primaryStage);
        customComponents(root);
        createButton();
        createScene(root);
    }

    private void createButton(){
        menuButton.setOnAction(e -> stage.setScene(new MenuView(new VBox(), stage)));
    }

    private void createScene(VBox root) {


        TableColumn<User, String> columnName = new TableColumn<>("Username");
        TableColumn<User, String> columnRole = new TableColumn<>("Role");
        table.getColumns().addAll(columnName, columnRole);
        columnName.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        columnRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        ObservableList<User> users = FXCollections.observableArrayList();
        users.add(new User(0, "Alice", "code1", User.ENGINEER));
        users.add(new User(1, "Bob", "code2"));
        table.setItems(users);


        // Selection d'un utilisateur et modifications de ses propriétés
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedUser) -> {

            if (selectedUser != null) {
                String userIdentifier = selectedUser.getIdentifier();
                String password = selectedUser.getPassword();
                String role = selectedUser.getRole();

                Label labelUserName = new Label("Username: " + userIdentifier);
                Label labelPassword = new Label("Password: " + password);
                Label labelNewUsername = new Label("New Username: ");
                Label labelNewPassword = new Label("New Password: ");
                TextField newUsernameTV = new TextField();
                TextField newPasswordTV = new TextField();
                HBox newUsernameBox = new HBox();
                HBox newPasswordBox = new HBox();
                newUsernameBox.getChildren().addAll(labelNewUsername,newUsernameTV);
                newPasswordBox.getChildren().addAll(labelNewPassword,newPasswordTV);
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
                VBox vbox = new VBox(labelUserName, labelPassword,newUsernameBox,newPasswordBox,toggleButtonsBox,saveButton);

                Scene scene = new Scene(vbox, 300, 200);

                Stage newStage = new Stage();

                newStage.setScene(scene);
                newStage.show();

                // Créer un événement pour le bouton "Save"

                saveButton.setOnAction(event -> {
                    // Mettre à jour les propriétés de l'utilisateur sélectionné
                    selectedUser.setIdentifier(newUsernameTV.getText());
                    selectedUser.setPassword(newPasswordTV.getText());
                    ToggleButton selectedToggle = (ToggleButton)toggleGroup.getSelectedToggle();
                    String newRole = selectedToggle.getText();
                    selectedUser.setRole(newRole);

                    // Fermer la fenêtre d'ajout d'utilisateur
                    newStage.close();
                    table.refresh();

                });



            }
        });

        // Ajout d'un utilisateur
        addUserButton.setOnAction(event -> {
            Stage stage = new Stage();
            VBox newRoot = new VBox();
            Scene scene = new Scene(newRoot, 300, 200);
            scene.setOnMouseClicked(e -> stage.requestFocus());

            Label label = new Label("Add new User");
            label.setGraphic(Assets.imageMap75.get("personAdd"));
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


            // Créer un événement pour le bouton "Save"
            saveButton.setOnAction(e -> {
                // Récupérer les valeurs saisies dans les champs de texte
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                ToggleButton selectedToggle = (ToggleButton)toggleGroup.getSelectedToggle();
                String role = selectedToggle.getText(); // Récupérer le rôle sélectionné

                // Ajouter un nouvel utilisateur à la liste existante
                User newUser = new User(0, username, password, role);
                users.add(newUser);

                // Fermer la fenêtre d'ajout d'utilisateur
                stage.close();
            });

            // Ajouter tous les éléments à la nouvelle scene
            newRoot.getChildren().addAll(label, usernameBox, passwordBox,toggleButtonsBox,saveButton);
            // Afficher la nouvelle fenêtre
            stage.setScene(scene);
            stage.show();
        });


        tableBox.getChildren().add(table);
        // Add Components to Regions children //
        titleBox.getChildren().add(titleLabel);
        root.getChildren().addAll(titleBox, addUserButton, tableBox, menuButton);

    }


    private void customComponents(VBox root) {
        menuButton.setGraphic(Assets.imageMap75.get("home"));
        menuButton.setContentDisplay(ContentDisplay.LEFT);
        root.setStyle("-fx-alignment: center");
        titleBox.setStyle("-fx-alignment: center");
        titleLabel.setStyle("-fx-font-size: 60px;" +
                "-fx-font-family: Times New Roman;" +
                "-fx-text-fill: white;" +
                "-fx-padding: 16px;");


    }
}

