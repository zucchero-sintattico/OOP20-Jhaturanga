package jhaturanga.model.timer;

import java.util.List;
import java.util.Map;

import jhaturanga.model.player.Player;

public interface TimerFactory {

    Timer defaultTimer(List<Player> players);

    Timer equalTimer(List<Player> players, double duration);

    Timer incrementableTimer(List<Player> players, double duration, double increment);

    Timer fromTimerMap(Map<Player, Double> durations);

}
