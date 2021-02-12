package jhaturanga.model.timer;

import java.util.HashMap;
import java.util.Map;

import jhaturanga.model.player.Player;

public final class TimerImpl implements Timer {

    private Map<Player, Integer> playersTimers = new HashMap<>();

    private boolean modificable = true;

    private Player actualPlayerTimer;
    private int initTimer;

    public TimerImpl(final Map<Player, Integer> playersTimers) {
        this.playersTimers = playersTimers;
    }

    @Override
    public int getRemaningTime(final Player player) {
        return playersTimers.get(player);
    }

    @Override
    public void start(final Player player) {
        // TODO Auto-generated method stub

    }

    @Override
    public void switchPlayer(final Player player) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isMoficicable() {
        return modificable;
    }

    public void setIsMoficicable(final boolean modificable) {
        this.modificable = modificable;
    }

    @Override
    public boolean updatePlayerTime(final Player player, final int second) {
        if (modificable) {
            this.playersTimers.put(player, second);
            return true;
        }
        return false;
    }

    @Override
    public boolean addTimeToPlayer(final Player player, final int seconds) {
        if (modificable) {
            this.playersTimers.replace(player, playersTimers.get(player) + seconds);
            return true;
        }
        return false;
    }

}
