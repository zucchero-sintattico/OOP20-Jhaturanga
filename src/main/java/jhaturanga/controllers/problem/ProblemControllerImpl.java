package jhaturanga.controllers.problem;

import java.util.Optional;

import jhaturanga.commons.PlayerPair;
import jhaturanga.controllers.AbstractController;
import jhaturanga.controllers.setup.WhitePlayerChoice;
import jhaturanga.model.chessproblems.ChessProblem;
import jhaturanga.model.chessproblems.ChessProblemsEnum;
import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.game.gametypes.GameTypeFactoryImpl;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.builder.MatchBuilderImpl;
import jhaturanga.model.timer.DefaultsTimersEnum;
import jhaturanga.model.user.management.UsersManager;

public final class ProblemControllerImpl extends AbstractController implements ProblemController {

    private ChessProblemsEnum problem;
    private final WhitePlayerChoice playerChoice = WhitePlayerChoice.FIRST_USER;
    private final DefaultsTimersEnum timer = DefaultsTimersEnum.NO_LIMIT;

    @Override
    public void setProblem(final ChessProblemsEnum problem) {
        this.problem = problem;
    }

    @Override
    public Optional<ChessProblemsEnum> getProblem() {
        return Optional.ofNullable(this.problem);
    }

    @Override
    public boolean createMatch() {
        if (this.problem == null) {
            return false;
        }

        final PlayerPair players = this.playerChoice.getPlayers(this.getApplicationInstance().getFirstUser().get(),
                UsersManager.COMPUTER);

        final ChessProblem chessProblem = this.problem.getChessProblem(players);

        final GameType chessGameType = new GameTypeFactoryImpl().chessProblemGameType(players, chessProblem);

        final Match match = new MatchBuilderImpl().gameType(chessGameType).timer(this.timer.getTimer(players)).build();

        this.getApplicationInstance().setMatch(match);
        return true;

    }

}
