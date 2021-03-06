package jhaturanga.controllers.game;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.piece.Piece;

public class MatchControllerImpl extends AbstractController implements MatchController {

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

    @Override
    public final String getWhiteReminingTime() {
        final int remainingSeconds = this.getModel().getTimer().get().getRemaningTime(this.getModel().getWhitePlayer());
        final int minutes = remainingSeconds / 60;
        final int seconds = remainingSeconds % 60;

        return minutes + ":" + seconds;
    }

    @Override
    public final String getBlackReminingTime() {
        final int remainingSeconds = this.getModel().getTimer().get().getRemaningTime(this.getModel().getBlackPlayer());
        final int minutes = remainingSeconds / 60;
        final int seconds = remainingSeconds % 60;

        return minutes + ":" + seconds;
    }

    @Override
    public final boolean isOver() {
        return this.getModel().getActualMatch().get().isCompleted();
    }

}
