package jhaturanga.model.match;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.controller.GameController;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.movement.PieceMovement;
import jhaturanga.model.movement.manager.MovementManager;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerPair;
import jhaturanga.model.timer.Timer;

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
     * Get the type of game of this match.
     * 
     * @return the type of game of this match.
     */
    GameType getType();

    /**
     * Get the timer instance of this match.
     * 
     * @return the timer
     */
    Timer getTimer();

    /**
     * Start the actual game.
     */
    void start();

    /**
     * Return the players.
     * 
     * @return the players
     */
    PlayerPair getPlayers();

    /**
     * Try to make a movement.
     * 
     * @param movement - the movement to make
     * @return true if the movement was made, false otherwise
     */
    MovementResult move(PieceMovement movement);

    /**
     * Get status of match.
     * 
     * @return EndGameType actual state of the match.
     */
    MatchStatus getMatchStatus();

    /**
     * Get the winner of this game but only if present.
     * 
     * @return the winner of this game, if present.
     */
    Optional<Player> getWinner();

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
     * 
     * @return list contain all board history
     */
    List<Board> getBoardFullHistory();

}
