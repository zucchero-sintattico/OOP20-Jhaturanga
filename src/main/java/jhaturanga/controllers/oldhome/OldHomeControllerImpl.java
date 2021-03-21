package jhaturanga.controllers.oldhome;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.timer.DefaultsTimers;
import jhaturanga.model.user.User;
import jhaturanga.model.user.management.UsersManager;

public final class OldHomeControllerImpl extends AbstractController implements OldHomeController {

    @Override
    public void setGameType(final GameTypesEnum gameType) {
        this.getModel().setGameType(gameType);
    }

    @Override
    public void setTimer(final DefaultsTimers timer) {
        this.getModel().setTimer(timer);
    }

    @Override
    public void createMatch() {
        this.getModel().createMatch();
    }

    @Override
    public boolean isGameTypePresent() {
        return this.getModel().getGameType().isPresent();
    }

    @Override
    public boolean isDynamicGameTypePresent() {
        return this.getModel().isDynamicGameTypeSet();
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

    @Override
    public void setupPlayers() {
        this.getModel().setWhitePlayer(new PlayerImpl(PlayerColor.WHITE, this.getFirstUser()));
        this.getModel().setBlackPlayer(new PlayerImpl(PlayerColor.BLACK, this.getSecondUser()));
    }

    @Override
    public String getGameTypeName() {
        return this.getModel().getGameTypeName();
    }

}
