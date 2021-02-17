package jhaturanga.controllers.game;

import jhaturanga.model.Model;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.views.View;

public class GameControllerImpl implements GameController {

    private View view;
    private final Model model;
    private int moveCounter;
    private int index;

    public GameControllerImpl(final Model model) {
        this.model = model;
    }

    @Override
    public final void setView(final View view) {
        this.view = view;
    }

    @Override
    public final View getView() {
        return this.view;
    }

    @Override
    public final boolean move(final BoardPosition origin, final BoardPosition destination) {
        if (this.model.getActualMatch().get().getBoard().getPieceAtPosition(origin).isPresent()) {
            final Piece piece = this.model.getActualMatch().get().getBoard().getPieceAtPosition(origin).get();
            if (this.model.getActualMatch().get().move(new MovementImpl(piece, origin, destination))) {
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
    public final Model getModel() {
        return this.model;
    }

    @Override
    public final Board getBoardStatus() {
        return this.model.getActualMatch().get().getBoard();
    }

    @Override
    public final Board getPrevBoard() {
        if (index > 0) {
            this.index--;
        }
        return this.model.getActualMatch().get().getBoardAtIndexFromHistory(index);
    }

    @Override
    public final Board getNextBoard() {
        if (index < this.moveCounter) {
            this.index++;
        }
        return this.model.getActualMatch().get().getBoardAtIndexFromHistory(index);
    }

    @Override
    public final boolean isInNavigationMode() {
        return this.index != this.moveCounter;
    }

}
