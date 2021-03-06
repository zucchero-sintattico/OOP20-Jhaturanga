package jhaturanga.controllers.home;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jhaturanga.controllers.AbstractController;
import jhaturanga.model.board.Board;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.match.Match;
import jhaturanga.model.player.Player;
import jhaturanga.model.timer.Timer;
import jhaturanga.model.user.User;
import jhaturanga.model.user.management.UsersManager;

public final class HomeControllerImpl extends AbstractController implements HomeController {

    @Override
    public void setGameType(final GameTypesEnum gameType) {
        this.getModel().setGameType(gameType);
    }

    @Override
    public void setTimer(final Timer timer) {
        this.getModel().setTimer(timer);
    }

    @Override
    public Match createMatch() {
        return this.getModel().createMatch();
    }

    @Override
    public List<String> getUserNameLoggedUsers() {
        return this.getModel().getLoggedUsers().stream().map(i -> i.getUsername()).collect(Collectors.toList());
    }

    @Override
    public int getNumbersOfLoggedUser() {
        return (int) this.getModel().getLoggedUsers().stream().filter(usr -> !usr.equals(UsersManager.GUEST)).count();

    }

    @Override
    public Optional<String> getNameGameTypeSelected() {
        if (this.getModel().getGameType().isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(this.getModel().getGameType().get().toString());

        }
    }

    @Override
    public void setWhitePlayer(final Player player) {
        this.getModel().setWhitePlayer(player);
    }

    @Override
    public void setBlackPlayer(final Player player) {
        this.getModel().setBlackPlayer(player);
    }

    @Override
    public List<User> getUsers() {
        return this.getModel().getLoggedUsers();
    }

    @Override
    public void addUser(final User user) {
        this.getModel().addLoggedUser(user);
    }

    @Override
    public List<Player> getPlayer() {
        return List.of(this.getModel().getWhitePlayer(), this.getModel().getBlackPlayer());
    }

    @Override
    public void logOut() {
        this.getModel().getLoggedUsers().remove(0);

    }

    @Override
    public List<Board> loadMatch() throws IOException, ClassNotFoundException {
        final FileInputStream fileIn = new FileInputStream("Test.txt");
        fileIn.close();
        final ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        objectIn.close();
        final Object obj = objectIn.readObject();
        return (List<Board>) obj;
    }

}
