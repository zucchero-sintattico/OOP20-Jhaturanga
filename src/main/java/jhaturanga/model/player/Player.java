package jhaturanga.model.player;

import jhaturanga.model.piece.factory.PieceFactory;

/**
 * 
 */
public interface Player {

    /**
     * Get the color of this player.
     * 
     * @return the color of the player.
     */
    PlayerColor getColor();

    /**
     * Get the name of the player.
     * 
     * @return the name
     */
    String getName();

    /**
     * Used to get the Player's PieceFactory.
     * 
     * @return PieceFactory is the specific Player's Piecefactory
     */
    PieceFactory getPieceFactory();
}
