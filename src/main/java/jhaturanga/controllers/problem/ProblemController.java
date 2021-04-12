package jhaturanga.controllers.problem;

import jhaturanga.controllers.Controller;
import jhaturanga.model.problems.Problems;

/**
 * The controller for the problem setup page view.
 *
 */
public interface ProblemController extends Controller {

    /**
     * Set the problem.
     * 
     * @param problem - the problem to be setted
     */
    void setProblem(Problems problem);

    /**
     * Create the match.
     * 
     * @return true if the match was created, false otherwise
     */
    boolean createMatch();
}
