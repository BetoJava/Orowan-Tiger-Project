module firstproject.firstproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens firstproject.firstproject to javafx.fxml;
    exports firstproject.firstproject;
    exports firstproject.firstproject.view;
    opens firstproject.firstproject.view to javafx.fxml;
}