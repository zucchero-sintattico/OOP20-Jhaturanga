package jhaturanga.model.match;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import jhaturanga.commons.Pair;
import jhaturanga.controllers.match.MovementResult;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.MatchStatusEnum;
import jhaturanga.model.game.GameController;
import jhaturanga.model.movement.Movement;
import jhaturanga.model.movement.MovementManager;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;

/**
 *
 */
public interface Match {

    /**
     * Get the actual game ID.
     * 
     * @return the ID of this match.
     */
    String getMatchID();

    /**
     * Start the actual game.
     */
    void start();

    /**
     * Try to make a movement.
     * 
     * @param movement - the movement to make
     * @return true if the movement was made, false otherwise
     */
    MovementResult move(Movement movement);

    /**
     * Get status of match.
     * 
     * @return EndGameType actual state of the match.
     */
    MatchStatusEnum matchStatus();

    /**
     * Get the winner of this game but only if present.
     * 
     * @return the winner of this game, if present.
     */
    Optional<Player> winner();

    /**
     * Use this method to get the board state at a wanted index.
     * 
     * @param index of which to get the movement
     * @return Board representing the wanted board state at the passed index
     */
    Board getBoardAtIndexFromHistory(int index);

    /**
     * Use this method to get the actual Board state.
     * 
     * @return Board representing the the state of the board
     */
    Board getBoard();

    /**
     * Get the game controller of this match.
     * 
     * @return the game controller
     */
    GameController getGameController();

    /**
     * Get the MovementManager of this match.
     * 
     * @return MovementManager
     */
    MovementManager getMovementManager();

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
     * Used to get the Player time remaining who's turn it is .
     * 
     * @return the Player time remaining who's turn it is
     */
    Pair<Player, Integer> getPlayerTimeRemaining();

    /**
     * 
     * @return list contain all board history
     */
    List<Board> getBoardFullHistory();

    /**
     * Use this method to upload a match history.
     * 
     * @param boardHistory - the List<Board> representing the History of the match
     *                     uploaded.
     */
    void uploadMatchHistory(List<Board> boardHistory);
}
