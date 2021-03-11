package jhaturanga.controllers.match;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;
import java.util.Set;

import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.controllers.AbstractController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;

public class MatchControllerImpl extends AbstractController implements MatchController {

    private static final int SECOND_IN_ONE_MINUTE = 60;
    private int moveCounter;
    private int index;

    @Override
    public final MovementResult move(final BoardPosition origin, final BoardPosition destination) {
        if (this.getModel().getActualMatch().get().getBoard().getPieceAtPosition(origin).isPresent()) {
            final Piece piece = this.getModel().getActualMatch().get().getBoard().getPieceAtPosition(origin).get();
            final MovementResult result = this.getModel().getActualMatch().get()
                    .move(new MovementImpl(piece, origin, destination));
            if (!result.equals(MovementResult.NONE)) {
                this.moveCounter++;
                // If a move is done then the index of the move watched has to be reset to the
                // new one
                this.index = this.moveCounter;
            }
            return result;

        }
        return MovementResult.NONE;
    }

    @Override
    public final Board getBoardStatus() {
        return this.getModel().getActualMatch().get().getBoard();
    }

    @Override
    public final Optional<Board> getPrevBoard() {
        if (index > 0) {
            this.index--;
            return Optional.of(this.getModel().getActualMatch().get().getBoardAtIndexFromHistory(index));
        }
        return Optional.empty();
    }

    @Override
    public final Optional<Board> getNextBoard() {
        if (index < this.moveCounter) {
            this.index++;
            return Optional.of(this.getModel().getActualMatch().get().getBoardAtIndexFromHistory(index));
        }
        return Optional.empty();
    }

    @Override
    public final boolean isInNavigationMode() {
        return this.index != this.moveCounter;
    }

    @Override
    public final void start() {
        this.getModel().getActualMatch().get().start();
    }

    private static String secondsToHumanReadableTime(final int seconds) {
        final int minutes = seconds / SECOND_IN_ONE_MINUTE;
        final int secondsFromMinutes = seconds % SECOND_IN_ONE_MINUTE;
        String humanTimeRepresentation = String.valueOf(minutes);
        if (minutes < 10) {
            humanTimeRepresentation = "0".concat(humanTimeRepresentation);
        }
        humanTimeRepresentation = humanTimeRepresentation.concat(":");

        if (secondsFromMinutes < 10) {
            humanTimeRepresentation = humanTimeRepresentation.concat("0");
        }

        humanTimeRepresentation = humanTimeRepresentation.concat(String.valueOf(secondsFromMinutes));
        return humanTimeRepresentation;
    }

    @Override
    public final String getWhiteReminingTime() {
        return secondsToHumanReadableTime(
                this.getModel().getTimer().get().getRemaningTime(this.getModel().getWhitePlayer()));
    }

    @Override
    public final String getBlackReminingTime() {
        return secondsToHumanReadableTime(
                this.getModel().getTimer().get().getRemaningTime(this.getModel().getBlackPlayer()));
    }

    @Override
    public final boolean isOver() {
        return this.getModel().getActualMatch().get().isCompleted();
    }

    @Override
    public final Set<BoardPosition> getPiecePossibleMoves(final Piece piece) {
        return this.getModel().getActualMatch().get().getPiecePossibleMoves(piece);
    }

    @Override
    public final void saveMatch() throws IOException {
        final long unixTime = System.currentTimeMillis() / 1000L;
        final String fileName = DirectoryConfigurations.CONFIGURATION_DIRECTORY_PATH + "/history/" + unixTime + ".txt";
        System.out.println(fileName);
        try (FileOutputStream fileOs = new FileOutputStream(fileName);
                ObjectOutputStream oosFile = new ObjectOutputStream(fileOs);) {
            oosFile.writeObject(this.getModel().getActualMatch().get().getBoardFullHistory());
        }
    }

    @Override
    public final Player getPlayerTurn() {
        return this.getModel().getActualMatch().get().getMovementManager().getPlayerTurn();
    }

}
