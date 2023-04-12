package firstproject.firstproject.view;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class View extends Scene {

    protected Stage stage;

    /**
     * Constructeur des différentes View
     * @param root Noeud racine sur lequel se fait l'affichage
     * @param primaryStage PrimaryStage
     */
    public View(VBox root, Stage primaryStage) {
        super(root, 1200, 700);
        this.stage = primaryStage;

    }
}
