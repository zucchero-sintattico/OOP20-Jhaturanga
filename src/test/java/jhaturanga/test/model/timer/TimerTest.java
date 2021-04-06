package jhaturanga.test.model.timer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.timer.Timer;
import jhaturanga.model.timer.TimerFactory;
import jhaturanga.model.timer.TimerFactoryImpl;
import jhaturanga.model.user.management.UsersManager;

class TimerTest {

    private static final double DEFAULT_SECONDS = 600;
    private static final double SECONDS_TEST_10 = 10;
    private static final double SECONDS_TEST_100 = 100;
    private static final double SECONDS_TEST_200 = 200;
    private final Player pl1 = new PlayerImpl(PlayerColor.WHITE, UsersManager.GUEST);
    private final Player pl2 = new PlayerImpl(PlayerColor.BLACK, UsersManager.GUEST);

    @Test
    void defaultTimerTest() {
        final List<Player> playerList = new ArrayList<>();
        // add 10min to all people
        playerList.add(pl1);
        playerList.add(pl2);

        final TimerFactory timerTest = new TimerFactoryImpl();
        final Timer defTimer = timerTest.defaultTimer(playerList);

        assertEquals(defTimer.getRemaningTime(pl1), DEFAULT_SECONDS); // 10 min
        assertEquals(defTimer.getRemaningTime(pl2), DEFAULT_SECONDS); // 10 min
    }

    @Test
    void equalTimerTest() {
        final List<Player> playerList = new ArrayList<>();
        playerList.add(pl1);
        playerList.add(pl2);

        final TimerFactory timerTest = new TimerFactoryImpl();
        final Timer defTimer = timerTest.equalTimer(playerList, DEFAULT_SECONDS);

        assertEquals(defTimer.getRemaningTime(pl1), DEFAULT_SECONDS); // 10 min
        assertEquals(defTimer.getRemaningTime(pl2), DEFAULT_SECONDS); // 10 min
    }

    @Test
    void fromTimerTest() {
        final Map<Player, Double> playersTimersMap = new HashMap<>();
        playersTimersMap.put(pl1, SECONDS_TEST_100);
        playersTimersMap.put(pl2, SECONDS_TEST_200);

        final TimerFactory timerTest = new TimerFactoryImpl();
        final Timer defTimer = timerTest.fromTimerMap(playersTimersMap);

        assertEquals(defTimer.getRemaningTime(pl1), SECONDS_TEST_100); // 10 min
        assertEquals(defTimer.getRemaningTime(pl2), SECONDS_TEST_200); // 10 min
    }

    @Test
    void genericTimerTest() {
        final Map<Player, Double> playersTimersMap = new HashMap<>();
        playersTimersMap.put(pl1, SECONDS_TEST_10);
        playersTimersMap.put(pl2, SECONDS_TEST_200);

        final TimerFactory timerTest = new TimerFactoryImpl();
        final Timer defTimer = timerTest.fromTimerMap(playersTimersMap);
        defTimer.start(pl1);
        try {
            TimeUnit.SECONDS.sleep(1);
            assertEquals(defTimer.getRemaningTime(pl1), SECONDS_TEST_10 - 1); // 9 sec remaining
            assertEquals(defTimer.getRemaningTime(pl2), SECONDS_TEST_200);
            defTimer.switchPlayer(pl2);
            TimeUnit.SECONDS.sleep(1);
            assertEquals(defTimer.getRemaningTime(pl1), SECONDS_TEST_10 - 1);
            assertEquals(defTimer.getRemaningTime(pl2), SECONDS_TEST_200 - 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
