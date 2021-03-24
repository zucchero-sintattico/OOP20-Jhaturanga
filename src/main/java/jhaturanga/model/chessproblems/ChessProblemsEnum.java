package jhaturanga.model.chessproblems;

import java.util.function.BiFunction;

import jhaturanga.model.player.Player;

public enum ChessProblemsEnum {
    /**
     * The first problem in the list.
     */
    PROBLEM_ONE((whitePlayer, blackPlayer) -> new ChessProblemFactoryImpl().problemOne(whitePlayer, blackPlayer)),

    /**
     * The first problem in the list.
     */
    PROBLEM_TWO((whitePlayer, blackPlayer) -> new ChessProblemFactoryImpl().problemTwo(whitePlayer, blackPlayer)),

    /**
     * The first problem in the list.
     */
    PROBLEM_THREE((whitePlayer, blackPlayer) -> new ChessProblemFactoryImpl().problemThree(whitePlayer, blackPlayer)),

    /**
     * The first problem in the list.
     */
    PROBLEM_FOUR((whitePlayer, blackPlayer) -> new ChessProblemFactoryImpl().problemFour(whitePlayer, blackPlayer));

    private final BiFunction<Player, Player, ChessProblem> chessProblemFunction;

    ChessProblemsEnum(final BiFunction<Player, Player, ChessProblem> chessProblem) {
        this.chessProblemFunction = chessProblem;
    }

    public ChessProblem getChessProblem(final Player whitePlayer, final Player blackPlayer) {
        return this.chessProblemFunction.apply(whitePlayer, blackPlayer);
    }

}
