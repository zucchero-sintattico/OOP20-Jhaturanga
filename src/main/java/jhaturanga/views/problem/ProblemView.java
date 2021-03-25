package jhaturanga.views.problem;

import jhaturanga.controllers.problem.ProblemController;
import jhaturanga.views.JavaFXView;

/**
 * The view for Game Type Select Page.
 */
public interface ProblemView extends JavaFXView {

    default ProblemController getProblemController() {
        return (ProblemController) this.getController();
    }
}
