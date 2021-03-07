package jhaturanga.controllers.home;

import java.util.List;
import java.util.Optional;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.Timer;
import jhaturanga.model.user.User;
import jhaturanga.model.user.management.UsersManager;

public final class HomeControllerImpl extends AbstractController implements HomeController {

    @Override
    public void setGameType(final GameType gameType) {
        this.getModel().setGameType(gameType);
    }

    @Override
    public void setTimer(final Optional<Timer> timer) {
        this.getModel().setTimer(timer.get());
    }

    @Override
    public Match createMatch() {
        return this.getModel().createMatch();
    }

    @Override
    public String getUserNameLoggedUsers() {

        final StringBuilder sb = new StringBuilder();

        for (final User usr : this.getModel().getLoggedUsers()) {
            sb.append(usr.getUsername());
            sb.append(' ');
        }

        return sb.toString();
    }

    @Override
    public int getNumbersOfLoggedUser() {
        return (int) this.getModel().getLoggedUsers().stream().filter(usr -> !usr.equals(UsersManager.GUEST)).count();
    }

    @Override
    public Optional<String> getNameGameTypeSelected() {
        if (this.getModel().getGameType().isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(this.getModel().getGameType().get().getGameName());

        }
    }

    @Override
    public void setWhitePlayer(final Player player) {
        this.getModel().setWhitePlayer(player);
    }

    @Override
    public void setBlackPlayer(final Player player) {
        this.getModel().setBlackPlayer(player);
    }

    @Override
    public List<User> getUsers() {
        return this.getModel().getLoggedUsers();
    }

    @Override
    public void addUser(final User user) {
        this.getModel().addLoggedUser(user);
    }

}
