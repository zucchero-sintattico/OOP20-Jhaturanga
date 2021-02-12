package jhaturanga.model.timer;

import java.util.HashMap;
import java.util.Map;

import jhaturanga.model.player.Player;

public final class TimerImpl implements Timer {

    private Map<Player, Integer> playersTimers = new HashMap<>();

    @Override
    public int getRemaningTime(final Player player) {
        return playersTimers.get(player);
    }

    @Override
    public void start(final Player player) {
        // TODO Auto-generated method stub

    }

    @Override
    public void switchPlayer() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isMoficicable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean updatePlayerTime(final Player player, final int second) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void addTimeToPlayer(final Player player, final int seconds) {
        // TODO Auto-generated method stub

    }

}
