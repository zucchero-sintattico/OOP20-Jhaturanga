package jhaturanga.controllers.setup;

import java.util.Optional;

import jhaturanga.controllers.BasicController;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.MatchImpl;
import jhaturanga.model.player.pair.PlayerPair;
import jhaturanga.model.timer.DefaultTimers;

public final class SetupControllerImpl extends BasicController implements SetupController {

    private GameType gameType;
    private DefaultTimers timer;
    private WhitePlayerChoice choice;

    @Override
    public void setGameType(final GameType gameType) {
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
    public Optional<GameType> getSelectedGameType() {
        return Optional.ofNullable(this.gameType);
    }

    @Override
    public Optional<DefaultTimers> getSelectedTimer() {
        return Optional.ofNullable(this.timer);
    }

    @Override
    public Optional<WhitePlayerChoice> getSelectedWhitePlayerChoice() {
        return Optional.ofNullable(this.choice);
    }

    @Override
    public boolean createMatch() {
        if (this.gameType == null || this.timer == null || this.choice == null) {
            return false;
        }
        final PlayerPair players = this.choice.getPlayers(this.getApplicationInstance().getFirstUser().get(),
                this.getApplicationInstance().getSecondUser().get());

        final Match match = new MatchImpl(this.gameType.getGameInstance(players), this.timer.getTimer(players));

        this.getApplicationInstance().setMatch(match);
        return true;
    }

}
