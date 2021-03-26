package jhaturanga.controllers.online;

import java.util.Optional;

import jhaturanga.controllers.AbstractController;
import jhaturanga.controllers.setup.SetupController;
import jhaturanga.controllers.setup.SetupControllerImpl;
import jhaturanga.controllers.setup.WhitePlayerChoice;
import jhaturanga.instance.ApplicationInstance;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.timer.DefaultTimers;

public final class OnlineSetupControllerImpl extends AbstractController implements OnlineSetupController {

    private final SetupController setupController = new SetupControllerImpl();

    @Override
    public void setApplicationInstance(final ApplicationInstance applicationInstance) {
        this.setupController.setApplicationInstance(applicationInstance);
        super.setApplicationInstance(applicationInstance);
    }

    @Override
    public void setGameType(final GameTypesEnum gameType) {
        this.setupController.setGameType(gameType);
    }

    @Override
    public void setTimer(final DefaultTimers timer) {
        this.setupController.setTimer(timer);
    }

    @Override
    public void setWhitePlayerChoice(final WhitePlayerChoice choice) {
        this.setupController.setWhitePlayerChoice(choice);
    }

    @Override
    public Optional<GameTypesEnum> getSelectedGameType() {
        return this.setupController.getSelectedGameType();
    }

    @Override
    public Optional<DefaultTimers> getSelectedTimer() {
        return this.setupController.getSelectedTimer();
    }

    @Override
    public Optional<WhitePlayerChoice> getSelectedWhitePlayerChoice() {
        return this.setupController.getSelectedWhitePlayerChoice();
    }

    @Override
    public boolean createMatch() {
        // CREATE A NEW NETWORK MATCH
        System.out.println("CREATE A NETWORK MATCH");
        return true;
    }

}
