package jhaturanga.views.setup;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jhaturanga.commons.console.CommandLineConsole;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.controllers.match.MatchControllerImpl;
import jhaturanga.controllers.setup.SetupController;
import jhaturanga.controllers.setup.WhitePlayerChoice;
import jhaturanga.model.game.type.GameType;
import jhaturanga.model.timer.DefaultTimers;
import jhaturanga.views.BasicView;
import jhaturanga.views.CommandLineView;
import jhaturanga.views.match.CommandLineMatchView;

/**
 * The command line version of the SetupView.
 */
public final class CommandLineSetupView extends BasicView implements CommandLineView {

    private final CommandLineConsole console = new CommandLineConsole();
    private boolean gameTypeSetted;
    private boolean timerSetted;
    private boolean whitePlayerSetted;

    private List<GameType> getPlayableGameTypes() {
        return Arrays.stream(GameType.values()).filter(x -> !x.equals(GameType.CHESS_PROBLEM))
                .filter(x -> !x.equals(GameType.CUSTOM_BOARD_VARIANT)).collect(Collectors.toList());
    }

    private void setupGameType() {
        while (!this.gameTypeSetted) {
            this.console.clearConsole();
            try {
                this.console.println("Select the game type: ");
                for (int i = 0; i < this.getPlayableGameTypes().size(); i++) {
                    this.console.println(i + " - " + this.getPlayableGameTypes().get(i).getName());
                }
                final Integer choice = Integer.valueOf(this.console.readLine("Enter selection: "));
                this.getSetupController().setGameType(this.getPlayableGameTypes().get(choice));
                this.gameTypeSetted = true;
                this.console.println("Game Type selected.");
            } catch (final NumberFormatException | IndexOutOfBoundsException e) {
                this.console.println("Enter a valid choice, press enter to try again...");
                this.console.readLine();
            }
        }

    }

    private void setupTimer() {
        while (!this.timerSetted) {
            this.console.clearConsole();
            try {
                this.console.println("Select the timer: ");
                for (int i = 0; i < DefaultTimers.values().length; i++) {
                    this.console.println(i + " - " + DefaultTimers.values()[i].toString());
                }
                final Integer choice = Integer.valueOf(this.console.readLine("Enter selection: "));
                this.getSetupController().setTimer(DefaultTimers.values()[choice]);
                this.timerSetted = true;
                this.console.println("Timer selected.");
            } catch (final NumberFormatException | IndexOutOfBoundsException e) {
                this.console.println("Enter a valid choice, press enter to try again...");
                this.console.readLine();
            }
        }

    }

    private void setupWhitePlayerChoice() {
        while (!this.whitePlayerSetted) {
            this.console.clearConsole();
            try {
                this.console.println("Select the white player choice: ");
                for (int i = 0; i < WhitePlayerChoice.values().length; i++) {
                    this.console.println(i + " - " + WhitePlayerChoice.values()[i].toString());
                }
                final Integer choice = Integer.valueOf(this.console.readLine("Enter selection: "));
                this.getSetupController().setWhitePlayerChoice(WhitePlayerChoice.values()[choice]);
                this.whitePlayerSetted = true;
                this.console.println("White Player Choiche selected.");
            } catch (final NumberFormatException | IndexOutOfBoundsException e) {
                this.console.println("Enter a valid choice, press enter to try again...");
                this.console.readLine();
            }
        }

    }

    @Override
    public void run() {
        this.console.clearConsole();
        this.setupGameType();
        this.setupTimer();
        this.setupWhitePlayerChoice();

        this.getSetupController().createMatch();
        this.goToMatchPage();

    }

    private void goToMatchPage() {
        new Thread(() -> {
            final CommandLineMatchView view = new CommandLineMatchView();
            final MatchController controller = new MatchControllerImpl();
            controller.setApplicationInstance(this.getController().getApplicationInstance());
            view.setController(controller);
            view.run();
        }).start();
    }

    private SetupController getSetupController() {
        return (SetupController) this.getController();
    }

}
