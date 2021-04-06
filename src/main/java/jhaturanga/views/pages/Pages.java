package jhaturanga.views.pages;

import java.util.function.Supplier;

import jhaturanga.controllers.BasicController;
import jhaturanga.controllers.Controller;
import jhaturanga.controllers.editor.EditorControllerImpl;
import jhaturanga.controllers.history.HistoryControllerImpl;
import jhaturanga.controllers.home.HomeControllerImpl;
import jhaturanga.controllers.leaderboard.LeaderboardControllerImpl;
import jhaturanga.controllers.loading.LoadingControllerImpl;
import jhaturanga.controllers.login.LoginControllerImpl;
import jhaturanga.controllers.match.MatchControllerImpl;
import jhaturanga.controllers.online.create.OnlineCreateControllerImpl;
import jhaturanga.controllers.online.join.OnlineJoinControllerImpl;
import jhaturanga.controllers.online.match.OnlineMatchControllerImpl;
import jhaturanga.controllers.problem.ProblemControllerImpl;
import jhaturanga.controllers.replay.ReplayControllerImpl;
import jhaturanga.controllers.settings.SettingsControllerImpl;
import jhaturanga.controllers.setup.SetupControllerImpl;

public enum Pages {

    /**
     * Loading screen.
     */
    LOADING("loading", LoadingControllerImpl::new),

    /**
     * Splash page.
     */
    SPLASH("splash", BasicController::new),

    /**
     * Home page.
     */
    HOME("home", HomeControllerImpl::new),

    /**
     * Leaderboard page.
     */
    LEADERBOARD("leaderboard", LeaderboardControllerImpl::new),

    /**
     * History page.
     */
    HISTORY("history", HistoryControllerImpl::new),

    /**
     * Settings page.
     */
    SETTINGS("settings", SettingsControllerImpl::new),

    /**
     * New Game page.
     */
    NEWGAME("newgame", BasicController::new),

    /**
     * Select Game page.
     */
    SELECT_GAME("selectgame", BasicController::new),

    /**
     * Game Type Selection page.
     */
    SETUP("setup", SetupControllerImpl::new),

    /**
     * Match page.
     */
    MATCH("match", MatchControllerImpl::new),

    /**
     * Login page.
     */
    LOGIN("login", LoginControllerImpl::new),

    /**
     * Editor page.
     */
    EDITOR("editor", EditorControllerImpl::new),

    /**
     * Problem setup page.
     */
    PROBLEM("problem", ProblemControllerImpl::new),

    /**
     * Replay page.
     */
    REPLAY("replay", ReplayControllerImpl::new),

    /**
     * Online main page.
     */
    ONLINE("online", BasicController::new),

    /**
     * 
     */
    ONLINE_CREATE("online-create", OnlineCreateControllerImpl::new),

    /**
     * 
     */
    ONLINE_JOIN("online-join", OnlineJoinControllerImpl::new),

    /**
     * 
     */
    ONLINE_MATCH("online-match", OnlineMatchControllerImpl::new);

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
