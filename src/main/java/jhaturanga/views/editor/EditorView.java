package jhaturanga.views.editor;

import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jhaturanga.commons.graphics.board.EditorBoard;
import jhaturanga.controllers.editor.EditorController;
import jhaturanga.controllers.setup.WhitePlayerChoice;
import jhaturanga.model.game.gametypes.GameTypesEnum;
import jhaturanga.model.timer.DefaultsTimersEnum;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public final class EditorView extends AbstractJavaFXView {

    @FXML
    private VBox whitePiecesSelector;

    @FXML
    private VBox blackPiecesSelector;

    @FXML
    private TextField columnsSelector;

    @FXML
    private TextField rowsSelector;

    @FXML
    private StackPane container;

    private EditorBoard editorBoard;

    @Override
    public void init() {
        this.editorBoard = new EditorBoard(this.getEditorController(), this, this.whitePiecesSelector,
                this.blackPiecesSelector);

        this.columnsSelector.setPromptText("COLUMNS[1-26]:");
        this.rowsSelector.setPromptText("ROWS[1-26]:");

        this.editorBoard.maxWidthProperty()
                .bind(Bindings.min(this.container.widthProperty(), this.container.heightProperty()));
        this.editorBoard.maxHeightProperty()
                .bind(Bindings.min(this.container.widthProperty(), this.container.heightProperty()));

        this.container.getChildren().add(this.editorBoard);

        this.getEditorController().setGameType(GameTypesEnum.CLASSIC_GAME);
        this.getEditorController().setTimer(DefaultsTimersEnum.TEN_MINUTES);
        this.getEditorController().setWhitePlayerChoice(WhitePlayerChoice.RANDOM);
    }

    @FXML
    public void changeBoardDimensions(final Event event) {
        if (this.checkIfInputIsCorrect()) {
            this.getEditorController().resetBoard(Integer.parseInt(this.columnsSelector.getText()),
                    Integer.parseInt(this.rowsSelector.getText()));
            this.editorBoard.drawBoard(this.getEditorController().getBoardStatus());
        }
    };

    private boolean checkIfInputIsCorrect() {
        try {
            Integer.parseInt(this.columnsSelector.getText());
            Integer.parseInt(this.rowsSelector.getText());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @FXML
    public void onBackClick(final ActionEvent event) throws IOException {
        PageLoader.switchPage(this.getStage(), Pages.SELECT_GAME, this.getEditorController().getApplicationInstance());
    };

    @FXML
    public void onStartClick(final Event event) throws IOException {
        this.getEditorController().createCustomizedStartingBoard();
        if (this.getEditorController().createMatch()) {
            PageLoader.switchPage(this.getStage(), Pages.MATCH, this.getEditorController().getApplicationInstance());
        }
    };

    public EditorController getEditorController() {
        return (EditorController) this.getController();
    }

}
