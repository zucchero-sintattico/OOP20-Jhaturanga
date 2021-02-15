package jhaturanga.commons.style;

public final class ApplicationStyleImpl implements ApplicationStyle {

    private ApplicationStyleEnum currentStyle = ApplicationStyleEnum.LIGHT;

    @Override
    public void setApplicationStyle(final ApplicationStyleEnum style) {
        this.currentStyle = style;

    }

    @Override
    public ApplicationStyleEnum getApplicationStyle() {
        return this.currentStyle;
    }

}
