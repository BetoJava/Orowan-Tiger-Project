package firstproject.firstproject.view;

import firstproject.firstproject.controller.Controller;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class View extends Scene {


    protected Stage stage;

    /**
     * Constructeur des différentes View
     * @param root Noeud racine sur lequel se fait l'affichage
     * @param primaryStage PrimaryStage
     * @param controller Controleur associé à la view
     */
    public View(VBox root, Stage primaryStage, Controller controller) {
        super(root);
        this.stage = primaryStage;

        //createContent(controller);


    }
}
