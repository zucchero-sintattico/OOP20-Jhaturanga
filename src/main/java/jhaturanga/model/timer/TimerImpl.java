package jhaturanga.model.timer;

import java.util.Map;
import java.util.Optional;

import jhaturanga.model.player.Player;

public final class TimerImpl implements Timer {

    private final Map<Player, Double> playersTimers;

    private boolean isRunning = true;
    private boolean isModifiable = true;

    private int increment;

    private Player actualPlayerTimer;
    private long initialUnixTime;

    public TimerImpl(final Map<Player, Double> playersTimers) {
        this.playersTimers = playersTimers;
    }

    @Override
    public double getRemaningTime(final Player player) {
        if (player.equals(this.actualPlayerTimer)) {
            final double numberOfSecondsUsed = (int) (System.currentTimeMillis() / 1000L - initialUnixTime);
            double remainingSecond = playersTimers.get(actualPlayerTimer) - numberOfSecondsUsed;
            if (remainingSecond < 0) {
                remainingSecond = 0;
            }
            return remainingSecond;
        }
        return playersTimers.get(player);
    }

    @Override
    public void start(final Player player) {
        this.actualPlayerTimer = player;
        this.initialUnixTime = System.currentTimeMillis() / 1000L;
        this.isRunning = true;
    }

    @Override
    public void switchPlayer(final Player player) {
        this.playersTimers.replace(actualPlayerTimer, getRemaningTime(actualPlayerTimer));
        this.start(player);
    }

    @Override
    public boolean isModifiable() {
        return isModifiable;
    }

    @Override
    public void setModifiable(final boolean modifiable) {
        this.isModifiable = modifiable;
    }

    @Override
    public void setIncrement(final int increment) {
        this.increment = increment;
    }

    @Override
    public int getIncrement() {
        return this.increment;
    }

    @Override
    public boolean updatePlayerTime(final Player player, final double second) {
        if (this.isModifiable) {
            this.playersTimers.replace(player, second);
            return true;
        }
        return false;
    }

    @Override
    public boolean addTimeToPlayer(final Player player, final double seconds) {
        return updatePlayerTime(player, seconds + this.playersTimers.get(player));
    }

    @Override
    public Optional<Player> getPlayersWithoutTime() {
        return playersTimers.entrySet().stream().filter(elem -> this.getRemaningTime(elem.getKey()) <= 0)
                .map(i -> i.getKey()).findAny();
    }

    @Override
    public void stop() {
        this.isRunning = false;
    }

    @Override
    public boolean isRunning() {
        return this.isRunning;
    }

}
