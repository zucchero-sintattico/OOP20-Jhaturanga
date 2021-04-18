package jhaturanga.model.timer;

/**
 * The Class TimerThread.
 */
public final class TimerThread extends Thread {

    /** The timer. */
    private final Timer timer;

    /** The call back. */
    private final Runnable callBack;

    /** The remaining time. */
    private final Runnable remainingTime;

    /** The Constant TIME_SLEEP. */
    private static final int TIME_SLEEP = 200;

    /**
     * Instantiates a new timer thread.
     *
     * @param timer         the timer
     * @param callBack      the call back
     * @param remainingTime the remaining time
     */
    public TimerThread(final Timer timer, final Runnable callBack, final Runnable remainingTime) {
        this.timer = timer;
        this.callBack = callBack;
        this.remainingTime = remainingTime;
    }

    /**
     * Run timer.
     */
    public void run() {
        while (this.timer.isRunning()) {

            if (this.timer.getPlayersWithoutTime().isPresent()) {
                callBack.run();
                break;
            }

            remainingTime.run();

            try {
                Thread.sleep(TIME_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
