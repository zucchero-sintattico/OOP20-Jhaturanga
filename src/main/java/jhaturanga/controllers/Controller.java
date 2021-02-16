package jhaturanga.controllers;

import jhaturanga.model.Model;
import jhaturanga.views.View;

/**
 * The generic template for a Controller.
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
     * Return the actual instance of the model. It is usefull for context switch.
     * 
     * @return the model
     */
    Model getModel();
}
