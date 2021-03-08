package jhaturanga.views.savedhistory;



import jhaturanga.controllers.savedhistory.SavedHistoryController;
import jhaturanga.views.AbstractView;

public final class HistoryViewImpl extends AbstractView implements HistoryView {



    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    @Override
    public SavedHistoryController getSavedHistoryController() {
        return (SavedHistoryController) this.getController();
    }

}
