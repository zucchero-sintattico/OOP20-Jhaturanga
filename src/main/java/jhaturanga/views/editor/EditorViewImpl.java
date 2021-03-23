package jhaturanga.views.editor;

import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import jhaturanga.controllers.editor.EditorController;
import jhaturanga.views.AbstractJavaFXView;
import jhaturanga.views.pages.PageLoader;
import jhaturanga.views.pages.Pages;

public class EditorViewImpl extends AbstractJavaFXView implements EditorView {

    @FXML
    private VBox whitePiecesSelector;

    @FXML
    private VBox blackPiecesSelector;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField columnsSelector;

    @FXML
    private TextField rowsSelector;

    @FXML
    private BorderPane grid;

    private EditorBoardView editorBoard;

    @Override
    public final void init() {
        this.editorBoard = new EditorBoardView(this.getEditorController(), this, this.whitePiecesSelector,
                this.blackPiecesSelector);
        this.columnsSelector.setPromptText("COLUMNS[0-30]:");
        this.rowsSelector.setPromptText("ROWS[0-30]:");
        this.grid.prefWidthProperty().bind(Bindings.min(this.root.widthProperty(), this.root.heightProperty()));
        this.grid.prefHeightProperty().bind(Bindings.min(this.root.widthProperty(), this.root.heightProperty()));
        this.grid.setCenter(this.editorBoard);
    }

    @FXML
    public final void changeBoardDimensions(final Event event) {
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
    public final void backToMenu(final Event event) throws IOException {
        this.getEditorController().getApplicationInstance().clearMatchInfo();
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getEditorController().getApplicationInstance());
    };

    @FXML
    public final void createBoard(final Event event) throws IOException {
        this.getEditorController().createCustomizedStartingBoard();
        PageLoader.switchPage(this.getStage(), Pages.HOME, this.getEditorController().getApplicationInstance());
    };

    @Override
    public final EditorController getEditorController() {
        return (EditorController) this.getController();
    }

}
