module firstproject.firstproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires javafx.graphics;


    exports firstproject.firstproject;
    exports firstproject.firstproject.assets;
    exports firstproject.firstproject.view;
    opens firstproject.firstproject.model to javafx.base;
    opens firstproject.firstproject.assets to javafx.graphics;
}