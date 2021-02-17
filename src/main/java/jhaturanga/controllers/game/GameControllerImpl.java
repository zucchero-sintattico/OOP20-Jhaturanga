package jhaturanga.controllers.game;

import jhaturanga.model.Model;
import jhaturanga.model.board.Board;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.views.View;

public class GameControllerImpl implements GameController {

    private View view;
    private Model model;

    public GameControllerImpl(final Model model) {
        this.model = model;
    }

    @Override
    public final void setView(final View view) {
        this.view = view;
    }

    @Override
    public final View getView() {
        return this.view;
    }

    @Override
    public final boolean move(final BoardPosition origin, final BoardPosition destination) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public final Model getModel() {
        return this.model;
    }

    @Override
    public Board getBoardStatus() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Board getPrevBoard() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Board getNextBoard() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isInNavigationMode() {
        // TODO Auto-generated method stub
        return false;
    }

}
