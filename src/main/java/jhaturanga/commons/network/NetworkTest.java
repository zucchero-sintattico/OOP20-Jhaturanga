package jhaturanga.commons.network;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.NetworkMatch;
import jhaturanga.model.user.management.UsersManager;

public final class NetworkTest {

    private NetworkTest() {

    }

    public static void main(final String[] args) throws MqttException, InterruptedException {

        final NetworkMatch match1 = new NetworkMatch(UsersManager.GUEST, () -> {
            System.out.println("MATCH 1 IS READY");
        });
        final NetworkMatch match2 = new NetworkMatch(UsersManager.GUEST, () -> {
            System.out.println("MATCH 2 IS READY");

        });

        final String matchId = match1.create(GameTypesEnum.CLASSIC_GAME, null);

        new Thread(() -> {
            match2.join(matchId);
        }).start();

    }

}
