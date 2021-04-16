package jhaturanga.views.commons.board.strategy.history;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * The Strategy for the key handling in a Match view to manage the navigation of
 * History.
 */
@FunctionalInterface
public interface HistoryKeyHandlerStrategy extends EventHandler<KeyEvent> {

    /**
     * Handle the key event to manage history navigation.
     * 
     * @param event - the key event
     */
    void handle(KeyEvent event);
}
