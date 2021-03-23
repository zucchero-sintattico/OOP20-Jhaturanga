package jhaturanga.views.home;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jhaturanga.commons.CommandLine;
import jhaturanga.controllers.home.HomeController;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.controllers.match.MatchControllerImpl;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.timer.DefaultsTimers;
import jhaturanga.views.AbstractView;
import jhaturanga.views.CommandLineView;
import jhaturanga.views.match.CommandLineMatchView;

public final class CommandLineHomeView extends AbstractView implements HomeView, CommandLineView {

    private final CommandLine console = new CommandLine();
    private final List<Player> players = new ArrayList<>();
    // We need to exclude the CustomBoardGameVariant from the possibilities because
    // it wouldn't be feasible to create a board from commandLine.
    private final List<GameTypesEnum> gameTypesList = Arrays.stream(GameTypesEnum.values())
            .filter(i -> !i.equals(GameTypesEnum.CUSTOM_BOARD_VARIANT)).collect(Collectors.toList());

    @Override
    public HomeController getHomeController() {
        return (HomeController) this.getController();
    }

    private void setupPlayers() {
        this.getHomeController().setWhitePlayer(new PlayerImpl(PlayerColor.WHITE, null));
        this.getHomeController().setBlackPlayer(new PlayerImpl(PlayerColor.BLACK, null));
        this.players.add(this.getHomeController().getModel().getWhitePlayer().get());
        this.players.add(this.getHomeController().getModel().getBlackPlayer().get());
    }

    private void setupTimer() {
        this.getHomeController().setTimer(DefaultsTimers.ONE_MINUTE);
    }

    private void setupGameType() {

        // Select Game Type
        this.console.println("Please select which game you want to play : ");

        this.gameTypesList.forEach(gameType -> {
            this.console.println("\t" + this.gameTypesList.indexOf(gameType) + " : " + gameType.toString());
        });

        boolean selected = false;
        while (!selected) {
            final String response = this.console.readLine("Select: ");
            if (this.isInputValid(response) && Integer.parseInt(response) >= 0
                    && Integer.parseInt(response) < this.gameTypesList.size()) {
                this.getHomeController().setGameType(this.gameTypesList.get(Integer.parseInt(response)));
                selected = true;
            }
        }
    }

    private boolean isInputValid(final String response) {
        try {
            Integer.parseInt(response);
            return !response.isEmpty() && !response.isBlank();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void goToGamePage() {
        new Thread(() -> {
            final CommandLineMatchView view = new CommandLineMatchView();
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
