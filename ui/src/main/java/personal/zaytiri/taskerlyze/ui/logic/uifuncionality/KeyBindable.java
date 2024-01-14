package personal.zaytiri.taskerlyze.ui.logic.uifuncionality;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyBindable {

    public void addEnterKeyBinding(Node handler, EventHandler<ActionEvent> attachedEventToHandler) {
        bindKey(KeyCode.ENTER, handler, attachedEventToHandler);
    }

    public void addEscapeKeyBinding(Node handler, EventHandler<ActionEvent> attachedEventToHandler) {
        bindKey(KeyCode.ESCAPE, handler, attachedEventToHandler);
    }

    private void bindKey(KeyCode keyCode, Node handler, EventHandler<ActionEvent> attachedEventToHandler) {
        handler.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == keyCode) {
                attachedEventToHandler.handle(new ActionEvent());
                ev.consume();
            }
        });
    }
}
