package jhaturanga.commons.network;

import java.io.Serializable;

import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.Timer;

/**
 * Data for an online match that will be shared from the creator of the match to
 * the user who join.
 *
 */
public final class NetworkMatchData implements Serializable {

    private static final long serialVersionUID = -1356536529148341191L;

    private final GameTypesEnum game;
    private final Player player;
    // private final Timer timer;

    public NetworkMatchData(final GameTypesEnum game, final Player player) {
        this.game = game;
        this.player = player;
    }

    /**
     * Get the Game Type of the match.
     * 
     * @return the game type
     */
    public GameTypesEnum getGameType() {
        return this.game;
    }

    /**
     * Get the player of the match.
     * 
     * @return the player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Get the timer of the match.
     * 
     * @return the timer
     */
    public Timer getTimer() {
        return null;
    }

}
