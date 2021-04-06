package jhaturanga.controllers;

import jhaturanga.instance.ApplicationInstance;
import jhaturanga.views.View;

public class BasicController implements Controller {

    private View view;
    private ApplicationInstance applicationInstance;

    @Override
    public final void setView(final View view) {
        this.view = view;
    }

    @Override
    public final View getView() {
        return this.view;
    }

    @Override
    public final ApplicationInstance getApplicationInstance() {
        return this.applicationInstance;
    }

    /**
     * 
     */
    @Override
    public void setApplicationInstance(final ApplicationInstance applicationInstance) {
        this.applicationInstance = applicationInstance;
    }

}
