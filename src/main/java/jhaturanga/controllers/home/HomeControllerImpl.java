package jhaturanga.controllers.home;

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
    public void setTimer(final Timer timer) {
        this.getModel().setTimer(timer);
    }

    @Override
    public Match createMatch() {
        return this.getModel().createMatch();
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
    public void setFirstUserGuest() {
        this.getModel().setFirstUser(UsersManager.GUEST);

    }

    @Override
    public void setSecondUserGuest() {
        this.getModel().setSecondUser(UsersManager.GUEST);

    }

    @Override
    public User getFirstUser() {
        return this.getModel().getFirstUser().get();
    }

    @Override
    public User getSecondUser() {
        return this.getModel().getSecondUser().get();
    }

    @Override
    public boolean isFirstUserLogged() {
        return this.getModel().getFirstUser().isPresent();
    }

    @Override
    public boolean isSecondUserLogged() {
        return this.getModel().getSecondUser().isPresent();
    }

}
