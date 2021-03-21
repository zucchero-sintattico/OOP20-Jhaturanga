package jhaturanga.model.timer;

import java.util.Optional;

public enum DefaultsTimers {
    /**
     * one minutes.
     */
    ONE_MINUTE("One Minute", 60),
    /**
     * two minutes.
     */
    THREE_MINUTE("Three Minute", 180),
    /**
     * ten minutes.
     */
    TEN_MINUTES("Ten Minute", 600),

    /**
     * 10 seconds.
     */
    TEN_SECONDS("Ten Seconds", 10),

    /**
     * 10 seconds with 1 second of increment.
     */
    TEN_SECONDS_PLUS_ONE("", 10, 1);

    private final String stringify;
    private final int seconds;
    private final int increment;

    DefaultsTimers(final String stringify, final int seconds) {
        this(stringify, seconds, 0);
    }

    DefaultsTimers(final String stringify, final int seconds, final Integer increment) {
        this.stringify = stringify;
        this.seconds = seconds;
        this.increment = increment;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public Optional<Integer> getIncrement() {
        return this.increment != 0 ? Optional.of(this.increment) : Optional.empty();
    }

    @Override
    public String toString() {
        return this.stringify;
    }

}
