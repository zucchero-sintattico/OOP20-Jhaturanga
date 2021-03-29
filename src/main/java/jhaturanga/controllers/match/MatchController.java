package jhaturanga.controllers.match;

import java.io.IOException;
import java.util.Set;

import jhaturanga.commons.Pair;
import jhaturanga.commons.graphics.strategy.history.HistoryNavigationController;
import jhaturanga.controllers.Controller;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.match.MatchStatusEnum;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.Timer;

/**
 * The controller for the game page.
 */
public interface MatchController extends Controller, HistoryNavigationController {

    /**
     * Move a piece.
     * 
     * @param origin      - the origin's position
     * @param destination - the destination's position
     * @return ActionType representing the actionType resulting from the action just
     *         performed
     */
    MovementResult move(BoardPosition origin, BoardPosition destination);

    /**
     * Get the white player.
     * 
     * @return the white player
     */
    Player getWhitePlayer();

    /**
     * Get the black player.
     * 
     * @return the black player
     */
    Player getBlackPlayer();

    /**
     * Get the players.
     * 
     * @return the players
     */
    Pair<Player, Player> getPlayers();

    /**
     * Return the timer of this match.
     * 
     * @return the timer
     */
    Timer getTimer();

    /**
     * Get the actual board status.
     * 
     * @return the status of the most recent board
     */
    Board getBoardStatus();

    /**
     * Used to get the Player whom turn it actually is.
     * 
     * @return Player representing the player who's turn it is.
     */
    Player getPlayerTurn();

    /**
     * Get the passed Piece possible BoardPositions where to move. This method is
     * mainly used to graphically represent them.
     * 
     * @param piece
     * @return Set<BoardPosition> representing the BoardPositions where the selected
     *         Piece can Move
     */
    Set<BoardPosition> getPiecePossibleMoves(Piece piece);

    /**
     * Check if the current game is not sync with the last move, in this case we are
     * navigation through the movement and we don't have to make any movement from
     * the GUI.
     * 
     * @return true if we are in navigation mode, false otherwise
     */
    boolean isInNavigationMode();

    /**
     * white remain time in second.
     * 
     * @return white remain time in second
     */
    double getWhiteRemainingTime();

    /**
     * white remain time in second.
     * 
     * @return white remain time in second
     */
    double getBlackRemainingTime();

    /**
     * start match.
     */
    void start();

    /**
     * Stop match's timer.
     */
    void stopTimer();

    /**
     * Get the status of the match.
     * 
     * @return EndGameType representing the status of the match when called.
     */
    MatchStatusEnum matchStatus();

    /**
     * save the match in a file.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    void saveMatch() throws IOException;

    /**
     * Delete the match.
     */
    void deleteMatch();

    /**
     * Check if the match is present.
     * 
     * @return true if the match is present, false otherwise
     */
    boolean isMatchPresent();
}
