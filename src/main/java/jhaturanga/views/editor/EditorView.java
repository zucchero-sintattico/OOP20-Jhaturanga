package jhaturanga.views.editor;

import jhaturanga.controllers.editor.EditorController;
import jhaturanga.views.JavaFXView;

public interface EditorView extends JavaFXView {

    /**
     * 
     * @return the editor controller
     */
    EditorController getEditorController();
}
