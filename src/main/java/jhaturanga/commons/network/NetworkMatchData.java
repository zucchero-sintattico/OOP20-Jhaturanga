package jhaturanga.commons.network;

import java.io.Serializable;

import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.timer.Timer;

public final class NetworkMatchData implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1356536529148341191L;

    private final GameTypesEnum game;
    private final String playerName;
    // private final Timer timer;

    public NetworkMatchData(final GameTypesEnum game, final String playerName) {
        this.game = game;
        this.playerName = playerName;
        // this.timer = timer;
    }

    public GameTypesEnum getGameType() {
        return this.game;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public Timer getTimer() {
        return null;
    }

}
