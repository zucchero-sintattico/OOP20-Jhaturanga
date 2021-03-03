package jhaturanga.model.timer;

public enum DefaultsTimers {
    /**
     * one minutes.
     */
    UN_MINUTO(60),
    /**
     * two minutes.
     */
    TRE_MINUTI(180),
    /**
     * ten minutes.
     */
    DIECI_MINUTI(600),

    /**
     * 10 seconds.
     */
    DIECI_SECONDI(10);

    private final int seconds;

    DefaultsTimers(final int secons) {
        this.seconds = secons;
    }

    public int getSeconds() {
        return this.seconds;
    }

}
