package jhaturanga.controllers;

import jhaturanga.model.Model;
import jhaturanga.views.View;

/**
 * A basic implementation of a Controller. This is used for view which doesn't
 * need access to model and as a superclass of other controllers.
 *
 */
public class BasicController implements Controller {

    private View view;
    private Model applicationInstance;

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setView(final View view) {
        this.view = view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final View getView() {
        return this.view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Model getApplicationInstance() {
        return this.applicationInstance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApplicationInstance(final Model applicationInstance) {
        this.applicationInstance = applicationInstance;
    }

}
