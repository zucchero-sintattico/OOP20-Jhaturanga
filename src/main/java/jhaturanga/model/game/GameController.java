package jhaturanga.model.game;

import java.util.List;

import jhaturanga.model.board.Board;
import jhaturanga.model.piece.movement.PieceMovementStrategyFactory;
import jhaturanga.model.player.Player;

public interface GameController {

    /**
     * Check if the game is over or not.
     * 
     * @return true if the game is completed, false otherwise
     */
    boolean isOver();

    /**
     * Check if the game is ended with a draw.
     * 
     * @return true if the game ended in a draw, false otherwise
     */
    boolean isDraw();

    /**
     * Control if the king is under check.
     * 
     * @param player is the player of which to check if the move puts his king in a
     *               check state
     * @return true if the king is under check
     */
    boolean isInCheck(Player player);

    /**
     * Return a boolean that states if the player passed as parameter won the game.
     * 
     * @param player is the player of which check if he won the game
     * @return true if player won false otherwise
     */
    boolean isWinner(Player player);

    /**
     * Return the state of the Board.
     * 
     * @return Board representing the actual state of the board of the match
     */
    Board boardState();

    /**
     * Return the list of the players.
     * 
     * @return List representing the players of the game
     */
    List<Player> getPlayers();

    /**
     * Return the PieceMovementStrategyFactory of the match's GameType that's been
     * controlled.
     * 
     * @return PieceMovementStrategyFactory representing the
     *         PieceMovementStrategyFactory of the game
     */
    PieceMovementStrategyFactory getPieceMovementStrategyFactory();
}
