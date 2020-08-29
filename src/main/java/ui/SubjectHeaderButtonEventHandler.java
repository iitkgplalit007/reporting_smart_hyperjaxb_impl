package ui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;

public class SubjectHeaderButtonEventHandler implements javafx.event.EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
        System.out.println(((Control)event.getSource()).getId());
    }
}
