package firstproject.firstproject;

import firstproject.firstproject.assets.Assets;
import firstproject.firstproject.controller.H2Database;
import firstproject.firstproject.model.*;
import firstproject.firstproject.view.LoginView;
import firstproject.firstproject.view.MenuView;
import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

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
        fillDatabase(h2);


        // Set up GUI //
        VBox root = new VBox();
        LoginView mainView = new LoginView(root, stage);
        stage.setTitle("TIGER - OROWAN");
        stage.getIcons().add(Assets.imageMap75.get("icon").getImage());
        stage.setScene(mainView);
        stage.show();

    }

    private void fillDatabase(H2Database h2) {
        if(h2.loadRawData(1939351,"2").isEmpty()) {
            h2.writeSensorData(Orowan.getRawData("1939351","2"));
            ArrayList<OrowanOutputData> outputData2 = Orowan.computeOrowanModel("1939351","2");
            h2.writeOrowanData(outputData2);
            ArrayList<ProcessedOutputData> processedOutputData2 = Orowan.getProcessedOutputData(outputData2, "1939351","2");
            h2.writeProcessedOutputData(processedOutputData2);
            h2.addStand("2");
            h2.addStandForUser(1, "2");
            h2.addStrip(1939351, "2");

        }

        if(h2.loadRawData(1939351,"3").isEmpty()) {
            h2.writeSensorData(Orowan.getRawData("1939351","3"));
            ArrayList<OrowanOutputData> outputData3 = Orowan.computeOrowanModel("1939351","3");
            h2.writeOrowanData(outputData3);
            ArrayList<ProcessedOutputData> processedOutputData3 = Orowan.getProcessedOutputData(outputData3, "1939351","3");
            h2.writeProcessedOutputData(processedOutputData3);
        }
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