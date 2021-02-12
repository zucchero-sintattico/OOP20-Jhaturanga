package jhaturanga.model.timer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jhaturanga.model.player.Player;

public final class TimerFactoryImpl implements TimerFactory {
    private static final int TEN_MINUTES = 600;

    @Override
    public Timer defaultTiemr(final List<Player> players) {
        final Map<Player, Integer> playerTimerMap = new HashMap<>();
        players.forEach((elem) -> playerTimerMap.put(elem, TEN_MINUTES));
        return new TimerImpl(playerTimerMap);
    }

    @Override
    public Timer equalTime(final List<Player> players, final int duration) {
        final Map<Player, Integer> playerTimerMap = new HashMap<>();
        players.forEach((elem) -> playerTimerMap.put(elem, duration));
        return new TimerImpl(playerTimerMap);
    }

    @Override
    public Timer fromTiemr(final Map<Player, Integer> durations) {
        return new TimerImpl(durations);
    }

}
