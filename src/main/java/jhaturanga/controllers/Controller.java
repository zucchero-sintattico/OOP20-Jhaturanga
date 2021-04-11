package jhaturanga.controllers;

import jhaturanga.model.Model;
import jhaturanga.views.View;

/**
 * The generic template for a Controller.
 * 
 */
public interface Controller {

    /**
     * Get the actual view of this controller.
     * 
     * @return the instance of the view attached to this controller.
     */
    View getView();

    /**
     * Set the view of this controller.
     * 
     * @param view - the view to be setted
     */
    void setView(View view);

    /**
     * Return the actual application instance. It is usefull for context switch.
     * 
     * @return the application instance
     */
    Model getApplicationInstance();

    /**
     * Set the application instance for this controller.
     * 
     * @param applicationInstance - the application instance to be setted
     */
    void setApplicationInstance(Model applicationInstance);
}
