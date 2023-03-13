package firstproject.firstproject.controller;

import firstproject.firstproject.Main;
import firstproject.firstproject.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Controller implements EventHandler<ActionEvent>, PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    @Override
    public void handle(ActionEvent event) {

    }

    public static void setView(View view) {
        Main.getStage().setScene(view);
    }
}
