package jhaturanga.model.timer;

import java.util.List;
import java.util.Map;

import jhaturanga.model.player.Player;

public interface TimerFactory {

    /**
     * 
     * @param players how wont assign timer
     * @return 10 minutes timer
     */
    Timer defaultTimer(List<Player> players);

    /**
     * 
     * @param players  players how wont assign timer
     * @param duration of timer in second
     * @return timer
     */
    Timer equalTimer(List<Player> players, double duration);

    /**
     * 
     * @param players   players players how wont assign timer
     * @param duration  of timer in second
     * @param increment who want set on timer
     * @return timer
     */
    Timer incrementableTimer(List<Player> players, double duration, int increment);

    /**
     * 
     * @param playersTimer to assign every player with a personal time
     * @return timer
     */
    Timer fromTimerMap(Map<Player, Double> playersTimer);

}
