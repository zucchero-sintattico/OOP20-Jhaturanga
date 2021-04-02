package jhaturanga.controllers.setup;

import java.util.Optional;

import jhaturanga.commons.PlayerPair;
import jhaturanga.controllers.AbstractController;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.builder.MatchBuilderImpl;
import jhaturanga.model.timer.DefaultsTimersEnum;

public final class SetupControllerImpl extends AbstractController implements SetupController {

    private GameTypesEnum gameType;
    private DefaultsTimersEnum timer;
    private WhitePlayerChoice choice;

    @Override
    public void setGameType(final GameTypesEnum gameType) {
        this.gameType = gameType;
    }

    @Override
    public void setTimer(final DefaultsTimersEnum timer) {
        this.timer = timer;
    }

    @Override
    public void setWhitePlayerChoice(final WhitePlayerChoice choice) {
        this.choice = choice;
    }

    @Override
    public Optional<GameTypesEnum> getSelectedGameType() {
        return Optional.ofNullable(this.gameType);
    }

    @Override
    public Optional<DefaultsTimersEnum> getSelectedTimer() {
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

        final Match match = new MatchBuilderImpl().gameType(this.gameType.getGeneratedGameType(players))
                .timer(this.timer.getTimer(players)).build();

        this.getApplicationInstance().setMatch(match);
        return true;
    }

}
