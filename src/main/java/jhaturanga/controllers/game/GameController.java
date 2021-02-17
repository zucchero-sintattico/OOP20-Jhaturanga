package jhaturanga.controllers.game;

import jhaturanga.controllers.Controller;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.views.game.GameView;

/**
 * The controller for the game page.
 */
public interface GameController extends Controller<GameView> {

    /**
     * Move a piece.
     * 
     * @param origin      - the origin's position
     * @param destination - the destination's position
     * @return true if the movement was made, false otherwise
     */
    boolean move(BoardPosition origin, BoardPosition destination);
}
