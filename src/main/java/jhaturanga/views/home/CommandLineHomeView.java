package jhaturanga.views.home;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jhaturanga.commons.CommandLine;
import jhaturanga.controllers.game.GameController;
import jhaturanga.controllers.game.GameControllerImpl;
import jhaturanga.controllers.home.HomeController;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.player.Player;
import jhaturanga.model.player.PlayerColor;
import jhaturanga.model.player.PlayerImpl;
import jhaturanga.model.timer.DefaultsTimers;
import jhaturanga.model.timer.TimerFactoryImpl;
import jhaturanga.views.AbstractView;
import jhaturanga.views.CommandLineView;
import jhaturanga.views.game.CommandLineGameView;

public final class CommandLineHomeView extends AbstractView implements HomeView, CommandLineView {

    private final CommandLine console = new CommandLine();
    private List<Player> players;

    @Override
    public HomeController getHomeController() {
        return (HomeController) this.getController();
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

        this.getHomeController().setPlayers(this.players);
    }

    private void setupTimer() {
        this.getHomeController().setTimer(
                Optional.of(new TimerFactoryImpl().equalTime(players, DefaultsTimers.UN_MINUTO.getSeconds())));
    }

    private void setupGameType() {

        // Select Game Type
        System.out.println("Please select which game you want to play : ");

        System.out.println("\t0 : Classic Game");

        System.out.println("\t1 : Pawn horde variant Game");

        System.out.println("\t2 : Pawns movement variant Game");

        System.out.println("\t3 : Piece swap variant Game");

        System.out.println("\t4 : Three columns variant Game");

        System.out.println("");

        boolean selected = false;
        while (!selected) {
            final String response = this.console.readLine("Select: ");
            switch (response) {
            case "0":
                this.getHomeController().setGameType(
                        GameTypesEnum.CLASSIC_GAME.getNewGameType(this.players.get(0), this.players.get(1)));
                selected = true;
                break;

            case "1":
                this.getHomeController().setGameType(
                        GameTypesEnum.PAWN_HORDE_VARIANT.getNewGameType(this.players.get(0), this.players.get(1)));
                selected = true;
                break;

            case "2":
                this.getHomeController().setGameType(
                        GameTypesEnum.PAWN_MOVEMENT_VARIANT.getNewGameType(this.players.get(0), this.players.get(1)));
                selected = true;
                break;

            case "3":
                this.getHomeController().setGameType(
                        GameTypesEnum.PIECE_SWAP_VARIANT.getNewGameType(this.players.get(0), this.players.get(1)));
                selected = true;
                break;

            case "4":
                this.getHomeController().setGameType(
                        GameTypesEnum.THREE_COLUMNS_VARIANT.getNewGameType(this.players.get(0), this.players.get(1)));
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
            final GameController controller = new GameControllerImpl();
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

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

}
