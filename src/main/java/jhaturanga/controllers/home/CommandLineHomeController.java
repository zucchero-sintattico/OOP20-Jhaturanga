package jhaturanga.controllers.home;

import java.util.List;

import jhaturanga.model.Model;
import jhaturanga.model.game.GameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.Timer;
import jhaturanga.views.View;

public final class CommandLineHomeController implements HomeController {

    private final Model model;
    private GameType gameType;
    private List<Player> players;
    private Timer timer;

    public CommandLineHomeController(final Model model) {
        this.model = model;
    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public void setView(final View view) {

    }

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
        return this.model.createMatch(this.gameType, this.timer, this.players);
    }

    @Override
    public Model getModel() {
        return this.model;
    }

}
