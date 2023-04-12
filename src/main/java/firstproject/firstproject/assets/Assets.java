package firstproject.firstproject.assets;

import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.HashMap;

public class Assets {

    public static HashMap<String, ImageView> imageMap75 = new HashMap<>();

    /**
     * Load every images.
     */
    public static void loadAssets() {
        URL statsPath = Assets.class.getClassLoader().getResource("firstproject/gui/stats.png");
        URL manageAccountPath = Assets.class.getClassLoader().getResource("firstproject/gui/manageAccount.png");
        URL settingsPath = Assets.class.getClassLoader().getResource("firstproject/gui/settings.png");
        URL doorPath = Assets.class.getClassLoader().getResource("firstproject/gui/door.png");
        URL homePath = Assets.class.getClassLoader().getResource("firstproject/gui/home.png");
        URL personAddPath = Assets.class.getClassLoader().getResource("firstproject/gui/personAdd.png");

        ImageView statsImage = new ImageView(statsPath.toExternalForm());
        ImageView usersImage = new ImageView(manageAccountPath.toExternalForm());
        ImageView settingsImage = new ImageView(settingsPath.toExternalForm());
        ImageView disconnectImage = new ImageView(doorPath.toExternalForm());
        ImageView homeImage = new ImageView(homePath.toExternalForm());
        ImageView personAddImage = new ImageView(personAddPath.toExternalForm());

        imageMap75.put("stats", setScale(statsImage, 0.75f));
        imageMap75.put("users", setScale(usersImage, 0.75f));
        imageMap75.put("settings", setScale(settingsImage, 0.75f));
        imageMap75.put("door", setScale(disconnectImage, 0.75f));
        imageMap75.put("home", setScale(homeImage, 0.75f));
        imageMap75.put("personAdd", setScale(personAddImage, 0.75f));

    }

    public static ImageView setScale(ImageView image, float size) {
        image.setScaleX(size);
        image.setScaleY(size);
        return image;
    }


}
