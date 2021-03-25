package jhaturanga.controllers.problem;

import java.util.Optional;

import jhaturanga.commons.Pair;
import jhaturanga.controllers.AbstractController;
import jhaturanga.controllers.setup.WhitePlayerChoice;
import jhaturanga.model.chessproblems.ChessProblem;
import jhaturanga.model.chessproblems.ChessProblemsEnum;
import jhaturanga.model.game.gametypes.GameType;
import jhaturanga.model.game.gametypes.GameTypeFactoryImpl;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.builder.MatchBuilderImpl;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.DefaultTimers;
import jhaturanga.model.user.management.UsersManager;

public final class ProblemControllerImpl extends AbstractController implements ProblemController {

    private ChessProblemsEnum problem;
    private final WhitePlayerChoice playerChoice = WhitePlayerChoice.FIRST_USER;
    private final DefaultTimers timer = DefaultTimers.NO_LIMIT;

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

        final Pair<Player, Player> players = this.playerChoice
                .getPlayers(this.getApplicationInstance().getFirstUser().get(), UsersManager.GUEST);

        final ChessProblem chessProblem = this.problem.getChessProblem(players.getX(), players.getY());

        final GameType chessGameType = new GameTypeFactoryImpl().chessProblemGameType(players.getX(), players.getY(),
                chessProblem);

        final Match match = new MatchBuilderImpl().gameType(chessGameType)
                .timer(this.timer.getTimer(players.getX(), players.getY())).build();

        this.getApplicationInstance().setMatch(match);
        return true;

    }

}
