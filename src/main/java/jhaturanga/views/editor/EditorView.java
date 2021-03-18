package jhaturanga.views.editor;

import jhaturanga.controllers.editor.EditorController;
import jhaturanga.views.View;

public interface EditorView extends View {

    /**
     * 
     * @return the editor controller
     */
    EditorController getEditorController();
}
