package jhaturanga.controllers.home;

import java.util.Optional;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.user.User;

public final class HomeControllerImpl extends AbstractController implements HomeController {

    @Override
    public Optional<User> getFirstUser() {
        return this.getApplicationInstance().getFirstUser();
    }

    @Override
    public Optional<User> getSecondUser() {
        return this.getApplicationInstance().getSecondUser();
    }

//    @Override
//    public void setupChessProblemAndCrateMatch() {
//        final ChessProblem chessProblem = ChessProblemsEnum.PROBLEM_ONE
//                .getChessProblem(this.getModel().getWhitePlayer().get(), this.getModel().getBlackPlayer().get());
//
//        final GameType chessGameType = new GameTypeFactoryImpl().chessProblemGameType(
//                this.getModel().getWhitePlayer().get(), this.getModel().getBlackPlayer().get(), chessProblem);
//
//        this.getModel().createMatch(new MatchImpl(chessGameType));
//    }

}
