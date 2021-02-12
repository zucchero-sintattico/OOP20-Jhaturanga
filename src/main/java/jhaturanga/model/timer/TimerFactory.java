package jhaturanga.model.timer;

import java.util.List;
import java.util.Map;

import jhaturanga.model.player.Player;

public interface TimerFactory {

    Timer defaultTimer(List<Player> players);

    Timer equalTime(List<Player> players, int duration);

    Timer fromTimerMap(Map<Player, Integer> durations);

}
