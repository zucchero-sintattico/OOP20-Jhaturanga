package jhaturanga.model.timer;

import java.util.stream.Collectors;

import jhaturanga.model.player.pair.PlayerPair;

/**
 * The Enum DefaultTimers.
 */
public enum DefaultTimers {

    /**
     * one minutes.
     */
    ONE_MINUTE("One Minute", 60),
    /**
     * two minutes.
     */
    THREE_MINUTES("Three Minutes", 180),
    /**
     * ten minutes.
     */
    TEN_MINUTES("Ten Minutes", 600),

    /**
     * 10 seconds.
     */
    TEN_SECONDS("Ten Seconds", 10),

    /**
     * 10 seconds with 1 second of increment.
     */
    TEN_SECONDS_PLUS_ONE("Ten Seconds + One", 10, 1),

    /**
     * Infinity timer.
     */
    NO_LIMIT("No limit", Double.POSITIVE_INFINITY);

    /** The stringify. */
    private final String stringify;

    /** The seconds. */
    private final double seconds;

    /** The increment. */
    private final int increment;

    /**
     * Instantiates a new default timers.
     *
     * @param stringify the stringify
     * @param seconds   the seconds
     */
    DefaultTimers(final String stringify, final double seconds) {
        this(stringify, seconds, 0);
    }

    /**
     * Instantiates a new default timers.
     *
     * @param stringify the stringify
     * @param seconds   the seconds
     * @param increment the increment
     */
    DefaultTimers(final String stringify, final double seconds, final Integer increment) {
        this.stringify = stringify;
        this.seconds = seconds;
        this.increment = increment;
    }

    /**
     * Gets the seconds.
     *
     * @return the seconds
     */
    public double getSeconds() {
        return this.seconds;
    }

    /**
     * Gets the timer.
     *
     * @param players the players
     * @return the timer of player
     */
    public Timer getTimer(final PlayerPair players) {
        final Timer timer = new TimerFactoryImpl().equalTimer(players.stream().collect(Collectors.toList()),
                this.seconds);
        timer.setIncrement(this.increment);
        return timer;
    }

    @Override
    public String toString() {
        return this.stringify;
    }

}
