package jhaturanga.model.timer;

public final class ObservableTimer extends Thread {

    private final Timer timer;

    private final Runnable callBack;
    private final Runnable remainingTime;

    public ObservableTimer(final Timer timer, final Runnable callBack, final Runnable remainingTime) {
        this.timer = timer;

        this.callBack = callBack;
        this.remainingTime = remainingTime;

    }

    public void run() {
        while (this.timer.isRunning()) {

            if (this.timer.getPlayerWithoutTime().isPresent()) {
                callBack.run();
                break;
            }

            remainingTime.run();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
