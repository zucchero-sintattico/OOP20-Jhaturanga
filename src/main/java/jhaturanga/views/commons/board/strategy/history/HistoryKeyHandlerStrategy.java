package jhaturanga.views.commons.board.strategy.history;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

@FunctionalInterface
public interface HistoryKeyHandlerStrategy extends EventHandler<KeyEvent> {

    /**
     * Handle the key event to manage history navigation.
     * 
     * @param event - the key event
     */
    void handle(KeyEvent event);
}
