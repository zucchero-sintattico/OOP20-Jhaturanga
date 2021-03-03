package jhaturanga.model.timer;

import java.util.Optional;

public enum DefaultsTimers {
    /**
     * one minutes.
     */
    UN_MINUTO(60, Optional.empty()),
    /**
     * two minutes.
     */
    TRE_MINUTI(180, Optional.empty()),
    /**
     * ten minutes.
     */
    DIECI_MINUTI(600, Optional.empty()),

    /**
     * 10 seconds.
     */
    DIECI_SECONDI(10, Optional.empty()),

    /**
     * 10 seconds with 1 second of increment.
     */
    DIECI_SECONDI_PIU_1(10, Optional.of(1));

    private final int seconds;
    private final Optional<Integer> increment;

    DefaultsTimers(final int secons, final Optional<Integer> increment) {
        this.seconds = secons;
        this.increment = increment;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public Optional<Integer> getIncrement() {
        return this.increment;
    }

}
