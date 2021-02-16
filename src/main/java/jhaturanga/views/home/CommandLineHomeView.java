package jhaturanga.views.home;

import java.util.ArrayList;
import java.util.List;

import javafx.stage.Stage;
import jhaturanga.commons.CommandLine;
import jhaturanga.controllers.Controller;
import jhaturanga.controllers.game.GameController;
import jhaturanga.controllers.game.GameControllerImpl;
import jhaturanga.controllers.home.HomeController;
import jhaturanga.model.game.ClassicGameType;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.views.CommandLineView;
import jhaturanga.views.game.CommandLineGameView;

public final class CommandLineHomeView implements HomeView, CommandLineView {

    private HomeController controller;
    private final CommandLine console;
    private List<Player> players;

    public CommandLineHomeView() {
        this.console = new CommandLine();
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Override
    public void setController(final Controller controller) {
        this.controller = (HomeController) controller;
    }

    @Override
    public Stage getStage() {
        return null;
    }

    @Override
    public void setStage(final Stage stage) {
    }

    private void setupPlayers() {

        // Select Players

        this.players = new ArrayList<>();

        final String whitePlayerName = this.console.readLine("Enter white player's name: ");
        final String blackPlayerName = this.console.readLine("Enter black player's name: ");

        final Player whitePlayer = new PlayerImpl(PlayerColor.WHITE, whitePlayerName);
        final Player blackPlayer = new PlayerImpl(PlayerColor.BLACK, blackPlayerName);

        this.players.add(whitePlayer);
        this.players.add(blackPlayer);

        this.controller.setPlayers(this.players);
    }

    private void setupGameType() {

        // Select Game Type
        System.out.println("Please select which game you want to play : ");

        System.out.println("\t0 : Classic Game");

        System.out.println("\t1 : Pawn movement variant Game");

        System.out.println("\t1 : Pawns horde variant Game");

        System.out.println("");

        boolean selected = false;
        while (!selected) {
            final String response = this.console.readLine("Select: ");
            switch (response) {
            case "0":
                this.controller.setGameType(new ClassicGameType(this.players.get(0), this.players.get(1)));
                selected = true;
                break;

            default:
                break;
            }
        }

    }

    private void goToGamePage() {

        new Thread(() -> {
            final CommandLineGameView view = new CommandLineGameView();
            final GameController controller = new GameControllerImpl(this.controller.getModel());
            view.setController(controller);

            view.run();
        }).start();

    }

    @Override
    public void run() {

        this.console.clearConsole();

        System.out.println("Welcome to Jhaturanga main menu.\n");

        this.setupPlayers();

        this.setupGameType();

        this.goToGamePage();

    }

}
