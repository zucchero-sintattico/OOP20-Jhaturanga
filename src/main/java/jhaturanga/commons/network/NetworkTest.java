package jhaturanga.commons.network;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.NetworkMatch;
import jhaturanga.model.user.management.UsersManager;

public final class NetworkTest {

    private NetworkTest() {

    }

    public static void main(final String[] args) throws MqttException, InterruptedException {

        final NetworkMatch match1 = new NetworkMatch(UsersManager.GUEST);
        final NetworkMatch match2 = new NetworkMatch(UsersManager.GUEST);

        final String matchId = match1.create(GameTypesEnum.CLASSIC_GAME, null);

        new Thread(() -> {
            match1.waitUntilPlayerJoin();
            System.out.println("MATCH 1 IS READY : BOARD = " + match1.getBoard());
        }).start();

        new Thread(() -> {
            match2.join(matchId);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("MATCH 2 IS READY : BOARD = " + match1.getBoard());
        }).start();

    }

}
