package jhaturanga.views.loading;

import jhaturanga.controllers.loading.LoadingController;
import jhaturanga.views.JavaFXView;

/**
 * The View for the Loading Page.
 */
public interface LoadingView extends JavaFXView {

    /**
     * Get the loading controller.
     * 
     * @return the controller
     */
    default LoadingController getLoadingController() {
        return (LoadingController) this.getController();
    };
}
