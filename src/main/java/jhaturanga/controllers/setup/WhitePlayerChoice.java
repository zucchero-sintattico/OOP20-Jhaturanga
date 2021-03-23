package jhaturanga.controllers.setup;

import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

import jhaturanga.commons.Pair;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.user.User;

public enum WhitePlayerChoice {

    /**
     * Set the first as white player.
     */
    FIRST_USER((first, second) -> first),

    /**
     * Set the second user as white player.
     */
    SECOND_USER((first, second) -> second),

    /**
     * Set a random player as white player.
     */
    RANDOM((first, second) -> List.of(first, second).get(new Random().nextInt(2)));

    private BiFunction<User, User, User> whiteUserChooser;

    WhitePlayerChoice(final BiFunction<User, User, User> whiteUserChooser) {
        this.whiteUserChooser = whiteUserChooser;
    }

    /**
     * Get the player from users.
     * 
     * @param firstUser  - the first user
     * @param secondUser - the second user
     * @return the player pair [ whitePlayer, blackPlayer ]
     */
    public Pair<Player, Player> getPlayers(final User firstUser, final User secondUser) {
        final User whiteUser = this.whiteUserChooser.apply(firstUser, secondUser);
        final Player whitePlayer = new PlayerImpl(PlayerColor.WHITE, whiteUser);
        final Player blackPlayer = new PlayerImpl(PlayerColor.BLACK,
                firstUser.equals(whiteUser) ? secondUser : firstUser);
        return new Pair<>(whitePlayer, blackPlayer);
    }
}
