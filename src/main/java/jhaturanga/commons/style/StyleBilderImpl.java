package jhaturanga.commons.style;

import javafx.stage.Stage;

public final class StyleBilderImpl implements StyleBilder {

    @Override
    public void setGameStyle(final GameStyle gameStyle) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setApplicationStyle(final ApplicationStyle applicationStyle, final Stage stage) {

        stage.getScene().setUserAgentStylesheet("css/dark.css");

    }

    public static void setDark(final Stage stage) {

        stage.getScene().setUserAgentStylesheet(ClassLoader.getSystemResource("css/dark.css").toString());
    }

}
