package jhaturanga.controllers;

import jhaturanga.model.Model;

/**
 * The generic template for a Controller.
 * 
 * @param <V> - the type of the view
 */
public interface Controller<V> {

    /**
     * Get the actual view of this controller.
     * 
     * @return the instance of the view attached to this controller.
     */
    V getView();

    /**
     * Set the view of this controller.
     * 
     * @param view - the view to be setted
     */
    void setView(V view);

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
