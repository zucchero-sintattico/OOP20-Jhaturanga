package jhaturanga.controllers.home;

import java.util.List;

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
    private Timer timer;

    @Override
    public void setGameType(final GameType gameType) {
        this.gameType = gameType;
    }

    @Override
    public void setPlayers(final List<Player> players) {
        this.players = players;
    }

    @Override
    public void setTimer(final Timer timer) {
        this.timer = timer;
    }

    @Override
    public Match createMatch() {
        return this.getModel().createMatch(this.gameType, this.timer, this.players);
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

}
