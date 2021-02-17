package jhaturanga.controllers;

import jhaturanga.model.Model;
import jhaturanga.pages.Page;
import jhaturanga.views.View;

/**
 * The generic template for a Controller.
 * 
 * @param <T> - the page
 */
public interface Controller<T extends Page> {

    /**
     * Get the actual view of this controller.
     * 
     * @return the instance of the view attached to this controller.
     */
    View<T> getView();

    /**
     * Set the view of this controller.
     * 
     * @param view - the view to be setted
     */
    void setView(View<T> view);

    /**
     * Return the actual instance of the model. It is usefull for context switch.
     * 
     * @return the model
     */
    Model getModel();

    /**
     * Set the model for this controller.
     * 
     * @param model - the model to be setted
     */
    void setModel(Model model);
}
