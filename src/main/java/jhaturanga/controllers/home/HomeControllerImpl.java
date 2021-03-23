package jhaturanga.controllers.home;

import java.util.Optional;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.timer.DefaultTimers;
import jhaturanga.model.user.User;
import jhaturanga.model.user.management.UsersManager;

public final class HomeControllerImpl extends AbstractController implements HomeController {

    @Override
    public void setGameType(final GameTypesEnum gameType) {
        this.getApplicationInstance().setGameType(gameType);
    }

    @Override
    public void setTimer(final DefaultTimers timer) {
        this.getApplicationInstance().setTimer(timer);
    }

    @Override
    public void createMatch() {
        this.getApplicationInstance().createMatch();
    }

    @Override
    public boolean isGameTypePresent() {
        return this.getApplicationInstance().getGameType().isPresent();
    }

    @Override
    public boolean isDynamicGameTypePresent() {
        return this.getApplicationInstance().isDynamicGameTypeSet();
    }

    @Override
    public void setWhitePlayer(final Player player) {
        this.getApplicationInstance().setWhitePlayer(player);
    }

    @Override
    public void setBlackPlayer(final Player player) {
        this.getApplicationInstance().setBlackPlayer(player);
    }

    @Override
    public void setFirstUserGuest() {
        this.getApplicationInstance().setFirstUser(UsersManager.GUEST);

    }

    @Override
    public void setSecondUserGuest() {
        this.getApplicationInstance().setSecondUser(UsersManager.GUEST);

    }

    @Override
    public Optional<User> getFirstUser() {
        return this.getApplicationInstance().getFirstUser();
    }

    @Override
    public Optional<User> getSecondUser() {
        return this.getApplicationInstance().getSecondUser();
    }

    @Override
    public boolean isFirstUserLogged() {
        return this.getApplicationInstance().getFirstUser().isPresent();
    }

    @Override
    public boolean isSecondUserLogged() {
        return this.getApplicationInstance().getSecondUser().isPresent();
    }

    @Override
    public void setupPlayers() {
        if (this.getFirstUser().isPresent() && this.getSecondUser().isPresent()) {
            this.getApplicationInstance().setWhitePlayer(new PlayerImpl(PlayerColor.WHITE, this.getFirstUser().get()));
            this.getApplicationInstance().setBlackPlayer(new PlayerImpl(PlayerColor.BLACK, this.getSecondUser().get()));
        }

    }

    @Override
    public Optional<String> getGameTypeName() {
        return this.getApplicationInstance().getSettedGameTypeName();
    }

}
