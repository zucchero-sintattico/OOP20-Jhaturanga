package jhaturanga.model.player.pair;

import java.util.stream.Stream;

import jhaturanga.model.player.Player;

/**
 * A pair of player used in game.
 */
public interface PlayerPair {

    /**
     * @return the white player
     */
    Player getWhitePlayer();

    /**
     * @return the black player
     */
    Player getBlackPlayer();

    /**
     * @return a stream of the two players.
     */
    Stream<Player> stream();
}
