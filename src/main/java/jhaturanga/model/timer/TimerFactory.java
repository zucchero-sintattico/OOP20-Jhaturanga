package jhaturanga.model.timer;

import java.util.List;
import java.util.Map;

import jhaturanga.model.player.Player;

public interface TimerFactory {

    Timer defaultTiemr(List<Player> players);

    Timer equalTime(List<Player> players, int duration);

    Timer fromTiemr(Map<Player, Integer> durations);

}
