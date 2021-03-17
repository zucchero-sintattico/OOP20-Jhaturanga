package jhaturanga.model.timer;

import java.util.List;
import java.util.Map;

import jhaturanga.model.player.Player;

public interface TimerFactory {

    Timer defaultTimer(List<Player> players);

    Timer equalTimer(List<Player> players, int duration);

    Timer incrementableTimer(List<Player> players, int duration, int increment);

    Timer fromTimerMap(Map<Player, Integer> durations);

}
