package jhaturanga.commons.style;

public interface ApplicationStyle {

    enum ApplicationStyleEnum {
        DARK, LIGHT
    }

    void setApplicationStyle(ApplicationStyleEnum style);

    ApplicationStyleEnum getApplicationStyle();

}
