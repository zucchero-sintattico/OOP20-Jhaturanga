package jhaturanga.model.player.pair;

import java.util.stream.Stream;

import jhaturanga.model.player.Player;

public interface PlayerPair {

    Player getWhitePlayer();

    Player getBlackPlayer();

    Stream<Player> stream();
}
