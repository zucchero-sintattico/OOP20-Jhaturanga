package jhaturanga.commons.network;

import org.eclipse.paho.client.mqttv3.MqttException;

import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.piece.Piece;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.user.management.UsersManager;

public final class NetworkTest {

    private NetworkTest() {

    }

    public static void main(final String[] args) throws MqttException, InterruptedException {

        final NetworkManager network1 = new NetworkManagerImpl();
        final NetworkManager network2 = new NetworkManagerImpl();

        final String gameId = network1.createMatch();
        System.out.println("GAME ID : " + gameId);

        Thread.sleep(1);

        network2.joinMatch(gameId);

        new Thread(() -> {
            final Player player = new PlayerImpl(PlayerColor.WHITE, UsersManager.GUEST);
            final Piece piece = player.getPieceFactory().getQueen(new BoardPositionImpl(3, 4));
            while (true) {
                network1.sendMove(new MovementImpl(piece, new BoardPositionImpl(4, 4)));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                network2.sendMove(new MovementImpl(piece, new BoardPositionImpl(4, 4)));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }

}
