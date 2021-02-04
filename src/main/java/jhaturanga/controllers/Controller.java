package jhaturanga.controllers;

import jhaturanga.views.View;

/**
 * 
 * @author Alessandro Mazzoli
 *
 */
public interface Controller {

	/**
	 * Get the actual view of this controller.
	 * @return the instance of the view attached to this controller.
	 */
	View getView();
}
