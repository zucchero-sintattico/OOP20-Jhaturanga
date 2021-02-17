package jhaturanga.controllers.home;

import java.util.List;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.game.GameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.Timer;

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

}
