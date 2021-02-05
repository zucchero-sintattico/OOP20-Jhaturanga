package jhaturanga.commons.style;

import javafx.scene.paint.Color;

public interface ApplicationStyle {
	
	/*
	 * @return unique style of application
	 */
	int getApplicatinStyleID();  // become a enum
	
	
	/*
	 * @return the base color of application style
	 */
	Color getPrimaryColor();

}
