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

    private GameType gameType;
    private List<Player> players;
    private Optional<Timer> timer;

    @Override
    public void setGameType(final GameType gameType) {
        this.gameType = gameType;
    }

    @Override
    public void setPlayers(final List<Player> players) {
        this.players = players;
    }

    @Override
    public void setTimer(final Optional<Timer> timer) {
        this.timer = timer;
    }

    @Override
    public Match createMatch() {
        return this.getModel().createMatch(this.gameType, this.timer);
    }

    @Override
    public String getUserNameLoggedUsers() {
        String usrStr = "";

        for (final User usr : this.getModel().getLoggedUsers()) {
            usrStr += usr.getUsername() + " ";
        }
        return usrStr;
    }

    @Override
    public int getNumbersOfLoggedUser() {
        return (int) this.getModel().getLoggedUsers().stream().filter(usr -> !usr.equals(UsersManager.GUEST)).count();

    }

    @Override
    public String getNameGameTypeSelected() {
        if (this.getModel().getGameType() == null) {
            return "select a type game";
        }
        return this.getModel().getGameType().getGameName();
    }

}
