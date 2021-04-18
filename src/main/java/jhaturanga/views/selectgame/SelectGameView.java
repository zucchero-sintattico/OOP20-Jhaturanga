package jhaturanga.views.selectgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

/**
 * The View where the user choose which type of game want to play.
 */
public final class SelectGameView extends AbstractJavaFXView {

    @Override
    public void init() {
    }

    @FXML
    public void onNormalClick(final ActionEvent event) {
        PageLoader.getInstance().switchPage(this.getStage(), Pages.SETUP,
                this.getController().getModel());
    }

    @FXML
    public void onCustomizedClick(final ActionEvent event) {
        PageLoader.getInstance().switchPage(this.getStage(), Pages.EDITOR,
                this.getController().getModel());
    }

    @FXML
    public void onProblemClick(final ActionEvent event) {
        PageLoader.getInstance().switchPage(this.getStage(), Pages.PROBLEM,
                this.getController().getModel());
    }

    @FXML
    public void onBackClick(final ActionEvent event) {
        PageLoader.getInstance().switchPage(this.getStage(), Pages.NEWGAME,
                this.getController().getModel());
    }
}
