package jhaturanga.model.player;

import jhaturanga.model.piece.factory.PieceFactory;

/**
 * 
 */
public interface Player {

    /*
     * TODO: User getUser();
     */

    /**
     * Get the color of this player.
     * 
     * @return the color of the player.
     */
    PlayerColor getColor();

    /**
     * Used to get the Player's PieceFactory.
     * 
     * @return PieceFactory is the specific Player's Piecefactory
     */
    PieceFactory getPieceFactory();
}
