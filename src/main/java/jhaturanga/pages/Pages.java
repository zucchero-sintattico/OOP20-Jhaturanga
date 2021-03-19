package jhaturanga.pages;

import java.util.function.Supplier;

import jhaturanga.controllers.Controller;
import jhaturanga.controllers.EmptyController;
import jhaturanga.controllers.editor.EditorControllerImpl;
import jhaturanga.controllers.gametypemenu.GameTypeControllerImpl;
import jhaturanga.controllers.history.HistoryControllerImpl;
import jhaturanga.controllers.home.HomeControllerImpl;
import jhaturanga.controllers.loading.LoadingControllerImpl;
import jhaturanga.controllers.login.LoginControllerImpl;
import jhaturanga.controllers.match.MatchControllerImpl;
import jhaturanga.controllers.savedhistory.SavedHistoryControllerImpl;
import jhaturanga.controllers.settings.SettingsControllerImpl;
import jhaturanga.controllers.splash.SplashControllerImpl;

public enum Pages {

    /**
     * Loading screen.
     */
    LOADING("loading", LoadingControllerImpl::new),

    /**
     * Splash page.
     */
    SPLASH("splash", SplashControllerImpl::new),

    /**
     * Home page.
     */
    HOME("home", HomeControllerImpl::new),

    /**
     * New Game page.
     */
    NEWGAME("newgame", EmptyController::new),

    /**
     * Login page.
     */
    LOGIN("login", LoginControllerImpl::new),

    /**
     * Register page.
     */
    REGISTER("register", EmptyController::new),

    /**
     * Game page.
     */
    GAME("game", MatchControllerImpl::new),

    /**
     * Editor page.
     */
    EDITOR("editor", EditorControllerImpl::new),

    /**
     * History page.
     */
    HISTORY("history", HistoryControllerImpl::new),

    /**
     * Settings page.
     */
    SETTINGS("settings", SettingsControllerImpl::new),

    /**
     * Game Type Menu page.
     */
    GAME_TYPE_MENU("gameTypeMenu", GameTypeControllerImpl::new),

    /**
     * Saved History page.
     */
    SAVED_HISTORY("savedHistory", SavedHistoryControllerImpl::new);

    private final String name;
    private final Supplier<Controller> controllerGenerator;

    Pages(final String name, final Supplier<Controller> controllerGenerator) {
        this.name = name;
        this.controllerGenerator = controllerGenerator;
    }

    public String getName() {
        return this.name;
    }

    public Controller getNewControllerInstance() {
        return this.controllerGenerator.get();
    }

}
