package jhaturanga.controllers.game;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.controllers.AbstractController;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.piece.Piece;

public class MatchControllerImpl extends AbstractController implements MatchController {

    private static final int SECOND_IN_ONE_MINUTE = 60;
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

    private static String secontToUmanMinutes(final int seconds) {
        final int minutes = seconds / SECOND_IN_ONE_MINUTE;
        final int secondsFromMinutes = seconds % SECOND_IN_ONE_MINUTE;
        String umanTimeRappresenation = String.valueOf(minutes);
        if (minutes < 10) {
            umanTimeRappresenation = "0" + umanTimeRappresenation;
        }
        umanTimeRappresenation = umanTimeRappresenation + ":";

        if (secondsFromMinutes < 10) {
            umanTimeRappresenation = umanTimeRappresenation + "0";
        }

        umanTimeRappresenation = umanTimeRappresenation + secondsFromMinutes;
        return umanTimeRappresenation;
    }

    @Override
    public final String getWhiteReminingTime() {
        return secontToUmanMinutes(this.getModel().getTimer().get().getRemaningTime(this.getModel().getWhitePlayer()));
    }

    @Override
    public final String getBlackReminingTime() {
        return secontToUmanMinutes(this.getModel().getTimer().get().getRemaningTime(this.getModel().getBlackPlayer()));
    }

    @Override
    public final boolean isOver() {
        return this.getModel().getActualMatch().get().isCompleted();
    }

    @Override
    public final void saveMatch() throws IOException {
        final long unixTime = System.currentTimeMillis() / 1000L;
        final String fileName = DirectoryConfigurations.CONFIGURATION_DIRECTORY_PATH + "/history/" + unixTime + ".txt";
        System.out.println(fileName);
        final FileOutputStream fileOs = new FileOutputStream(fileName);
        final ObjectOutputStream oosFile = new ObjectOutputStream(fileOs);
        oosFile.writeObject(this.getModel().getActualMatch().get().getBoardFullHistory());
        oosFile.close();

    }

}
