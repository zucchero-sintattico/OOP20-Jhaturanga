package jhaturanga.views.loading;

import jhaturanga.controllers.loading.LoadingController;
import jhaturanga.views.View;

public interface LoadingView extends View {

    /**
     * Get the loading controller.
     * 
     * @return the controller
     */
    LoadingController getLoadingController();
}
