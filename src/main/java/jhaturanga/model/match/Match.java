package jhaturanga.model.match;

import java.util.Optional;
import java.util.Set;

import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.game.Game;
import jhaturanga.model.history.History;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.movement.PieceMovement;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.pair.PlayerPair;
import jhaturanga.model.timer.Timer;

/**
 * The entity for the Match.
 */
public interface Match {

    /**
     * Get the actual game ID.
     * 
     * @return the ID of this match.
     */
    String getMatchID();

    /**
     * Return the players.
     * 
     * @return the players
     */
    PlayerPair getPlayers();

    /**
     * 
     * @return the game
     */
    Game getGame();

    /**
     * Get the timer instance of this match.
     * 
     * @return the timer
     */
    Timer getTimer();

    /**
     * Get the history.
     * 
     * @return the history
     */
    History getHistory();

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
    MovementResult move(PieceMovement movement);

    /**
     * Get status of match.
     * 
     * @return EndGameType actual state of the match.
     */
    MatchStatus getMatchStatus();

    /**
     * Get the end type.
     * 
     * @return the end type
     */
    Optional<MatchEndType> getEndType();

    /**
     * Get the winner of this game but only if present.
     * 
     * @return the winner of this game, if present.
     */
    Optional<Player> getWinner();

    /**
     * Use this method to get the actual Board state.
     * 
     * @return Board representing the the state of the board
     */
    Board getBoard();

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
     * Resign the player.
     * 
     * @param player - the player to resign
     */
    void resign(Player player);

}
