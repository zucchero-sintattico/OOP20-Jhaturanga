package jhaturanga.model.game;

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
}
