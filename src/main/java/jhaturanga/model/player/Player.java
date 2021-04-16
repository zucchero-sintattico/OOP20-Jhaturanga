package jhaturanga.model.player;

import java.io.Serializable;

import jhaturanga.model.piece.factory.PieceFactory;
import jhaturanga.model.user.User;

/**
 * The entity for the Player management.
 */
public interface Player extends Serializable {

    /**
     * Get the color of this player.
     * 
     * @return the color of the player.
     */
    PlayerColor getColor();

    /**
     * Get the userName of the player.
     * 
     * @return the username
     */
    String getUserName();

    /**
     * Get the User of the player.
     * 
     * @return the User of the player
     */
    User getUser();

    /**
     * Used to get the Player's PieceFactory.
     * 
     * @return PieceFactory is the specific Player's Piecefactory
     */
    PieceFactory getPieceFactory();
}
