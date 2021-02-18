package jhaturanga.model.timer;

public enum DefaultsTimers {
    /**
     * one minutes.
     */
    UN_MUNUTO(60),
    /**
     * two minutes.
     */
    TRE_MUNUTI(180),
    /**
     * ten minutes.
     */
    DIECI_MUNUTI(600);

    private int seconds;

    DefaultsTimers(final int secons) {
        this.seconds = secons;
    }

    public int getSeconds() {
        return this.seconds;
    }

}
