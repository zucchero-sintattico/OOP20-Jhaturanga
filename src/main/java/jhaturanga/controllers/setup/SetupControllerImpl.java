package jhaturanga.controllers.setup;

import jhaturanga.commons.Pair;
import jhaturanga.controllers.AbstractController;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.MatchImpl;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.DefaultTimers;

public final class SetupControllerImpl extends AbstractController implements SetupController {

    private GameTypesEnum gameType;
    private DefaultTimers timer;
    private WhitePlayerChoice choice;

    @Override
    public void setGameType(final GameTypesEnum gameType) {
        this.gameType = gameType;
    }

    @Override
    public void setTimer(final DefaultTimers timer) {
        this.timer = timer;
    }

    @Override
    public void setWhitePlayerChoice(final WhitePlayerChoice choice) {
        this.choice = choice;
    }

    @Override
    public boolean createMatch() {
        if (this.gameType == null || this.timer == null || this.choice == null) {
            return false;
        }
        final Pair<Player, Player> players = this.choice.getPlayers(this.getApplicationInstance().getFirstUser().get(),
                this.getApplicationInstance().getSecondUser().get());

        final Match match = new MatchImpl(this.gameType.getGameType(players.getX(), players.getY()),
                this.timer.getTimer(players.getX(), players.getY()));

        this.getApplicationInstance().setMatch(match);
        return true;
    }

}
