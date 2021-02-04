package jhaturanga.views;

import jhaturanga.controllers.Controller;

/**
 * The generic template for a View.
 * @author Alessandro Mazzoli
 *
 */
public interface View {
	
	/**
	 * Get the actual controller of this view.
	 * @return the instance of the controller attached to this view.
	 */
	Controller getController();
	
	
	/**
	 * Set the controller for this view.
	 * @param controller - the controller to be attached to this view.
	 */
	void setController(final Controller controller);
	
}
