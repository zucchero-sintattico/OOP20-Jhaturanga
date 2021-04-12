package jhaturanga.controllers.problem;

import jhaturanga.controllers.BasicController;
import jhaturanga.controllers.setup.WhitePlayerChoice;
import jhaturanga.model.game.Game;
import jhaturanga.model.game.factory.GameFactoryImpl;
import jhaturanga.model.match.Match;
import jhaturanga.model.match.MatchImpl;
import jhaturanga.model.player.pair.PlayerPair;
import jhaturanga.model.problems.Problem;
import jhaturanga.model.problems.Problems;
import jhaturanga.model.timer.DefaultTimers;
import jhaturanga.model.user.management.UsersManager;

/**
 * Basic implementation of the ProblemController.
 *
 */
public final class ProblemControllerImpl extends BasicController implements ProblemController {

    private Problems problem;
    private final WhitePlayerChoice playerChoice = WhitePlayerChoice.FIRST_USER;
    private final DefaultTimers timer = DefaultTimers.NO_LIMIT;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProblem(final Problems problem) {
        this.problem = problem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean createMatch() {
        if (this.problem == null) {
            return false;
        }

        final PlayerPair players = this.playerChoice.getPlayers(this.getApplicationInstance().getFirstUser().get(),
                UsersManager.COMPUTER);

        final Problem chessProblem = this.problem.getChessProblem(players);

        final Game chessGameType = new GameFactoryImpl().chessProblemGameType(players, chessProblem);

        final Match match = new MatchImpl(chessGameType, this.timer.getTimer(players));

        this.getApplicationInstance().setMatch(match);
        return true;

    }

}
