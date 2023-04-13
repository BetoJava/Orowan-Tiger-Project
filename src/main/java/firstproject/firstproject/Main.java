package firstproject.firstproject;

import firstproject.firstproject.assets.Assets;
import firstproject.firstproject.controller.H2Database;
import firstproject.firstproject.model.Stand;
import firstproject.firstproject.model.User;
import firstproject.firstproject.view.LoginView;
import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage stage;

    private static User currentUser;

    /**
     * Méthode de lancement du programme
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Méthode de démarrage de la classe Application. Instancie les principales instances que sont l'Accumulateur,
     * le Controleur et l'interface graphique (GUI). Met en place le titre, l'icon et la view de la fenêtre,
     * de l'application et l'affiche.
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        Assets.loadAssets();

        H2Database h2 = H2Database.getInstance();
        H2Database.setUserIsEngineer(true);
        h2.addUser("jb","jb",true);
        h2.addStandForUser(h2.getUsers().get(0).getId(), "F2");


        // Set up GUI //
        VBox root = new VBox();
        LoginView mainView = new LoginView(root, stage);
        stage.setTitle("FIRST Project");
        //stage.getIcons().add(new Image("./icon.png"));
        stage.setScene(mainView);
        stage.show();

    }

    public static Stage getStage() {
        return stage;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Main.currentUser = currentUser;
    }
}