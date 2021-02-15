package jhaturanga.controllers;

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
}
