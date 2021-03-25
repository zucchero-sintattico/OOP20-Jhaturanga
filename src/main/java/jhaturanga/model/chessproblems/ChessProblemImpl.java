package jhaturanga.model.chessproblems;

import java.util.List;

import jhaturanga.model.board.Board;
import jhaturanga.model.movement.BasicMovement;

public final class ChessProblemImpl implements ChessProblem {

    private final List<BasicMovement> correctMoves;
    private final Board problemStartingBoard;

    public ChessProblemImpl(final List<BasicMovement> correctMoves, final Board problemStartingBoard) {
        this.problemStartingBoard = problemStartingBoard;
        this.correctMoves = correctMoves;
    }

    @Override
    public List<BasicMovement> getCorrectMoves() {
        return this.correctMoves;
    }

    @Override
    public Board getProblemStartingBoard() {
        return this.problemStartingBoard;
    }

}
