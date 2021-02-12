package jhaturanga.test.timer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.timer.Timer;
import jhaturanga.model.timer.TimerFactory;
import jhaturanga.model.timer.TimerFactoryImpl;

class TimerTest {

    private static final int DEFAULT_SECONDS = 600;
    private static final int SECONDS_TEST_100 = 100;
    private static final int SECONDS_TEST_200 = 200;
    private final Player pl1 = new PlayerImpl(PlayerColor.WHITE);
    private final Player pl2 = new PlayerImpl(PlayerColor.BLACK);

    @Test
    void defaultTiemrTest() {
        final List<Player> playerList = new ArrayList<>();
        // add 10min to all people
        playerList.add(pl1);
        playerList.add(pl2);

        final TimerFactory timerTest = new TimerFactoryImpl();
        final Timer defTiemr = timerTest.defaultTimer(playerList);

        assertEquals(defTiemr.getRemaningTime(pl1), DEFAULT_SECONDS); // 10 min
        assertEquals(defTiemr.getRemaningTime(pl2), DEFAULT_SECONDS); // 10 min
    }

    @Test
    void equalTimeTest() {
        final List<Player> playerList = new ArrayList<>();
        playerList.add(pl1);
        playerList.add(pl2);

        final TimerFactory timerTest = new TimerFactoryImpl();
        final Timer defTiemr = timerTest.equalTime(playerList, DEFAULT_SECONDS);

        assertEquals(defTiemr.getRemaningTime(pl1), DEFAULT_SECONDS); // 10 min
        assertEquals(defTiemr.getRemaningTime(pl2), DEFAULT_SECONDS); // 10 min
    }

    @Test
    void fromTiemrTest() {
        final Map<Player, Integer> playersTimersMap = new HashMap<>();
        playersTimersMap.put(pl1, SECONDS_TEST_100);
        playersTimersMap.put(pl2, SECONDS_TEST_200);

        final TimerFactory timerTest = new TimerFactoryImpl();
        final Timer defTiemr = timerTest.fromTimerMap(playersTimersMap);

        assertEquals(defTiemr.getRemaningTime(pl1), SECONDS_TEST_100); // 10 min
        assertEquals(defTiemr.getRemaningTime(pl2), SECONDS_TEST_200); // 10 min
    }

}
