package jhaturanga.commons.network;

import java.io.Serializable;

import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.Timer;

public final class NetworkMatchData implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1356536529148341191L;

    private final GameTypesEnum game;
    private final Player player;
    // private final Timer timer;

    public NetworkMatchData(final GameTypesEnum game, final Player player) {
        this.game = game;
        this.player = player;
    }

    public GameTypesEnum getGameType() {
        return this.game;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Timer getTimer() {
        return null;
    }

}
