package firstproject.firstproject.assets;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.HashMap;

public class Assets {

    public static HashMap<String, ImageView> imageMap = new HashMap<>();
    public static Image backgroundImage;

    /**
     * Load every images.
     */
    public static void loadAssets() {
        URL iconPath= Assets.class.getClassLoader().getResource("firstproject/gui/icon.png");
        URL statsPath = Assets.class.getClassLoader().getResource("firstproject/gui/stats.png");
        URL manageAccountPath = Assets.class.getClassLoader().getResource("firstproject/gui/manageAccount.png");
        URL settingsPath = Assets.class.getClassLoader().getResource("firstproject/gui/settings.png");
        URL doorPath = Assets.class.getClassLoader().getResource("firstproject/gui/door.png");
        URL homePath = Assets.class.getClassLoader().getResource("firstproject/gui/home.png");
        URL personAddPath = Assets.class.getClassLoader().getResource("firstproject/gui/personAdd.png");
        URL tigerBackground = Assets.class.getClassLoader().getResource("firstproject/gui/tiger.jpg");

        ImageView iconImage = new ImageView(iconPath.toExternalForm());
        ImageView statsImage = new ImageView(statsPath.toExternalForm());
        ImageView usersImage = new ImageView(manageAccountPath.toExternalForm());
        ImageView settingsImage = new ImageView(settingsPath.toExternalForm());
        ImageView disconnectImage = new ImageView(doorPath.toExternalForm());
        ImageView homeImage = new ImageView(homePath.toExternalForm());
        ImageView personAddImage = new ImageView(personAddPath.toExternalForm());
        backgroundImage = new Image(tigerBackground.toExternalForm());

        imageMap.put("blackStats", setScale(new ImageView(statsPath.toExternalForm()), 0.75f));
        imageMap.put("blackHome", setScale(new ImageView(homePath.toExternalForm()), 0.75f));
        imageMap.put("blackPersonAdd", setScale(new ImageView(personAddPath.toExternalForm()), 0.75f));

        for(ImageView imageView : new ImageView[]{statsImage, usersImage, settingsImage, disconnectImage, homeImage, personAddImage}) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(1.0);
            colorAdjust.setSaturation(1.0);
            colorAdjust.setHue(0.0);
            imageView.setEffect(colorAdjust);
        }
        imageMap.put("icon", setScale(iconImage, 0.75f));
        imageMap.put("stats", setScale(statsImage, 0.75f));
        imageMap.put("users", setScale(usersImage, 0.75f));
        imageMap.put("settings", setScale(settingsImage, 0.75f));
        imageMap.put("door", setScale(disconnectImage, 0.75f));
        imageMap.put("home", setScale(homeImage, 0.75f));
        imageMap.put("personAdd", setScale(personAddImage, 0.75f));

    }

    /**
     * Réduit la taille de l'image en entrée et la renvoie.
     */
    public static ImageView setScale(ImageView image, float size) {
        image.setScaleX(size);
        image.setScaleY(size);
        return image;
    }


}
