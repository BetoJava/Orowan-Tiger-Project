package firstproject.firstproject.view;

import firstproject.firstproject.controller.Controller;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserManagerView extends View {

    /**
     * Constructeur de la View graphique
     *
     * @param root         Noeud racine sur lequel se fait l'affichage
     * @param primaryStage PrimaryStage
     * @param controller   Controleur associé à la view
     */
    public UserManagerView(VBox root, Stage primaryStage, Controller controller) {
        super(root, primaryStage, controller);
        root.getChildren().add(new Label("FUIzeguyi"));
    }
}
