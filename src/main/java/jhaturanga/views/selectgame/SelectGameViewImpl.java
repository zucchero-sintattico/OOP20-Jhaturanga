package jhaturanga.views.selectgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

/**
 * Basic implementation for the Splash View.
 */
public final class SelectGameViewImpl extends AbstractJavaFXView implements SelectGameView {

    @Override
    public void init() {
//        this.getStage().setMinWidth(this.getStage().getWidth());
//        this.getStage().setMinHeight(this.getStage().getHeight());
    }

    @FXML
    public void onNormalClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.SETUP, this.getController().getApplicationInstance());
    }

    @FXML
    public void onCustomizedClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.EDITOR, this.getController().getApplicationInstance());
    }

    @FXML
    public void onProblemClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.PROBLEM, this.getController().getApplicationInstance());
    }

    @FXML
    public void onBackClick(final ActionEvent event) {
        PageLoader.switchPage(this.getStage(), Pages.NEWGAME, this.getController().getApplicationInstance());
    }
}
