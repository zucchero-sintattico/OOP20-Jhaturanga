package jhaturanga.controllers;

import jhaturanga.model.Model;
import jhaturanga.views.View;

public class BasicController implements Controller {

    private View view;
    private Model applicationInstance;

    @Override
    public final void setView(final View view) {
        this.view = view;
    }

    @Override
    public final View getView() {
        return this.view;
    }

    @Override
    public final Model getApplicationInstance() {
        return this.applicationInstance;
    }

    /**
     * 
     */
    @Override
    public void setApplicationInstance(final Model applicationInstance) {
        this.applicationInstance = applicationInstance;
    }

}
