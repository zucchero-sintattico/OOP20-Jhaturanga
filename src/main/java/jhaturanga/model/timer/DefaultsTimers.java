package jhaturanga.model.timer;

import java.util.Optional;

public enum DefaultsTimers {
    /**
     * one minutes.
     */
    ONE_MINUTE(60, Optional.empty()),
    /**
     * two minutes.
     */
    THREE_MINUTE(180, Optional.empty()),
    /**
     * ten minutes.
     */
    TEN_MINUTES(600, Optional.empty()),

    /**
     * 10 seconds.
     */
    TEN_SECONDS(10, Optional.empty()),

    /**
     * 10 seconds with 1 second of increment.
     */
    TEN_SECONDS_PLUS_ONE(10, Optional.of(1)),

    /**
     * Infinity timer.
     */
    NO_LIMIT(Double.POSITIVE_INFINITY, Optional.empty());

    private final double seconds;
    private final Optional<Integer> increment;

    DefaultsTimers(final double secons, final Optional<Integer> increment) {
        this.seconds = secons;
        this.increment = increment;

    }

    public double getSeconds() {
        return this.seconds;
    }

    public Optional<Integer> getIncrement() {
        return this.increment;
    }

}
