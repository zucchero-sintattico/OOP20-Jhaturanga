package jhaturanga.commons.style;

import javafx.scene.paint.Color;

public interface ApplicationStyle {

    enum Style {
        DARK, LIGHT
    }

    /*
     * @return unique style of application
     */
    int getApplicatinStyleID(Style style);

    /*
     * @return the base color of application style
     */
    Color getPrimaryColor();

}
