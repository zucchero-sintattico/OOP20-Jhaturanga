package jhaturanga.views.home;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import jhaturanga.commons.CommandLine;
import jhaturanga.controllers.game.MatchController;
import jhaturanga.controllers.game.MatchControllerImpl;
import jhaturanga.controllers.home.HomeController;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.timer.DefaultsTimers;
import jhaturanga.views.AbstractView;
import jhaturanga.views.CommandLineView;
import jhaturanga.views.game.commandline.CommandLineGameView;

public final class CommandLineHomeView extends AbstractView implements HomeView, CommandLineView {

    private final CommandLine console = new CommandLine();
    private final List<Player> players = new ArrayList<>();

    @Override
    public HomeController getHomeController() {
        return (HomeController) this.getController();
    }

    private void setupPlayers() {
        // TODO: AIUTO DA REFACTORARE COL NUOVO CONTROLLER
        this.getHomeController().setWhitePlayer(new PlayerImpl(PlayerColor.WHITE, null));
        this.getHomeController().setBlackPlayer(new PlayerImpl(PlayerColor.BLACK, null));
        this.players.add(this.getHomeController().getModel().getWhitePlayer());
        this.players.add(this.getHomeController().getModel().getBlackPlayer());
    }

    private void setupTimer() {
        this.getHomeController().setTimer(DefaultsTimers.ONE_MINUTE);
    }

    private void setupGameType() {

        // Select Game Type
        System.out.println("Please select which game you want to play : ");

        Stream.iterate(0, x -> x + 1).limit(GameTypesEnum.values().length).forEach(i -> {
            System.out.println("\t" + i + " : "
                    + GameTypesEnum.values()[i].getGameType(this.players.get(0), this.players.get(1)).getGameName());
        });

        boolean selected = false;
        while (!selected) {
            final String response = this.console.readLine("Select: ");
            if (this.isInputValid(response) && Integer.parseInt(response) >= 0
                    && Integer.parseInt(response) < GameTypesEnum.values().length) {
                this.getHomeController().setGameType(GameTypesEnum.values()[Integer.parseInt(response)]);
                selected = true;
            }
        }
    }

    private boolean isInputValid(final String response) {
        try {
            Integer.parseInt(response);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void goToGamePage() {

        new Thread(() -> {
            final CommandLineGameView view = new CommandLineGameView();
            final MatchController controller = new MatchControllerImpl();
            controller.setModel(this.getController().getModel());
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

        this.setupTimer();

        this.getHomeController().createMatch();

        this.goToGamePage();

    }

    // TODO: DA USARE?
    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

}
