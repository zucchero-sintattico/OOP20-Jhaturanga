package jhaturanga.model.timer;

import java.util.List;
import java.util.Map;

import jhaturanga.model.player.Player;

/**
 * A factory for creating Timer objects.
 */
public interface TimerFactory {

    /**
     * Default timer.
     *
     * @param players how wont assign timer
     * @return 10 minutes timer
     */
    Timer defaultTimer(List<Player> players);

    /**
     * Equal timer for every player.
     *
     * @param players  players how wont assign timer
     * @param duration of timer in second
     * @return timer
     */
    Timer equalTimer(List<Player> players, double duration);

    /**
     * Incrementable timer: can add time run time.
     *
     * @param players   players players how wont assign timer
     * @param duration  of timer in second
     * @param increment who want set on timer
     * @return timer
     */
    Timer incrementableTimer(List<Player> players, double duration, int increment);

    /**
     * From timer map: <player, player time>.
     *
     * @param playersTimer to assign every player with a personal time
     * @return timer
     */
    Timer fromTimerMap(Map<Player, Double> playersTimer);

}
