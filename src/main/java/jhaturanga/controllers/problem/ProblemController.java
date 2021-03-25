package jhaturanga.controllers.problem;

import java.util.Optional;

import jhaturanga.controllers.Controller;
import jhaturanga.model.chessproblems.ChessProblemsEnum;

public interface ProblemController extends Controller {

    /**
     * Set the problem.
     * 
     * @param problem - the problem to be setted
     */
    void setProblem(ChessProblemsEnum problem);

    /**
     * Get the selected problem.
     * 
     * @return the selected problem, if present
     */
    Optional<ChessProblemsEnum> getProblem();

    /**
     * Create the match.
     * 
     * @return true if the match was created, false otherwise
     */
    boolean createMatch();
}
