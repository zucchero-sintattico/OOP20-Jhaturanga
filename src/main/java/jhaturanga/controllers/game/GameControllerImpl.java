package jhaturanga.controllers.game;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.piece.Piece;

public class GameControllerImpl extends AbstractController implements GameController {

    private int moveCounter;
    private int index;

    @Override
    public final boolean move(final BoardPosition origin, final BoardPosition destination) {
        if (this.getModel().getActualMatch().get().getBoard().getPieceAtPosition(origin).isPresent()) {
            final Piece piece = this.getModel().getActualMatch().get().getBoard().getPieceAtPosition(origin).get();
            if (this.getModel().getActualMatch().get().move(new MovementImpl(piece, origin, destination))) {
                this.moveCounter++;
                // If a move is done then the index of the move watched has to be reset to the
                // new one
                this.index = this.moveCounter;
                return true;
            }
        }
        return false;
    }

    @Override
    public final Board getBoardStatus() {
        return this.getModel().getActualMatch().get().getBoard();
    }

    @Override
    public final Board getPrevBoard() {
        if (index > 0) {
            this.index--;
        }
        return this.getModel().getActualMatch().get().getBoardAtIndexFromHistory(index);
    }

    @Override
    public final Board getNextBoard() {
        if (index < this.moveCounter) {
            this.index++;
        }
        return this.getModel().getActualMatch().get().getBoardAtIndexFromHistory(index);
    }

    @Override
    public final boolean isInNavigationMode() {
        return this.index != this.moveCounter;
    }

    @Override
    public final void start() {
        this.getModel().getActualMatch().get().start();

    }

}
