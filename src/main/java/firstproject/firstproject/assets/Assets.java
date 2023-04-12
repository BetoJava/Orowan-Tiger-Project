package firstproject.firstproject.assets;

import javafx.scene.image.ImageView;

public class Assets {

    public static ImageView graphsImage;
    public static  ImageView usersImage;
    public static  ImageView settingsImage;
    public static  ImageView disconnectImage;

    /**
     * Load every images.
     */
    public static void loadAssets() {
        String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/firstproject/";
        graphsImage = new ImageView("file://" + resourcesPath + "gui/stats.png");
        usersImage = new ImageView("file://" + resourcesPath + "gui/manageAccount.png");
        settingsImage = new ImageView("file://" + resourcesPath + "gui/settings.png");
        disconnectImage = new ImageView( "file://" + resourcesPath + "gui/door.png");
    }



}
