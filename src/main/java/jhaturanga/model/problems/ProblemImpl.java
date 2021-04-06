package jhaturanga.model.problems;

import java.util.List;

import jhaturanga.model.board.Board;
import jhaturanga.model.movement.BasicMovement;

public final class ProblemImpl implements Problem {

    private final List<BasicMovement> correctMoves;
    private final Board problemStartingBoard;

    public ProblemImpl(final List<BasicMovement> correctMoves, final Board problemStartingBoard) {
        this.problemStartingBoard = problemStartingBoard;
        this.correctMoves = correctMoves;
    }

    @Override
    public List<BasicMovement> getCorrectMoves() {
        return this.correctMoves;
    }

    @Override
    public Board getStartingBoard() {
        return this.problemStartingBoard;
    }

}
