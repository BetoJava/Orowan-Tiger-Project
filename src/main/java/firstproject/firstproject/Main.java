package firstproject.firstproject;

import firstproject.firstproject.controller.Controller;
import firstproject.firstproject.view.MainView;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage stage;

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

        Controller controller = new Controller();

        // Set up GUI //
        VBox root = new VBox();
        MainView mainView = new MainView(root, stage, controller);
        stage.setTitle("FIRST Project");
        //stage.getIcons().add(new Image("./icon.png"));
        Controller.setView(mainView);
        stage.show();

    }

    public static Stage getStage() {
        return stage;
    }
}