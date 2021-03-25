package jhaturanga.model.chessproblems;

import java.util.function.BiFunction;

import jhaturanga.model.player.Player;

public enum ChessProblemsEnum {
    /**
     * The first problem in the list.
     */
    PROBLEM_ONE("Problem 1",
            (whitePlayer, blackPlayer) -> new ChessProblemFactoryImpl().problemOne(whitePlayer, blackPlayer)),

    /**
     * The first problem in the list.
     */
    PROBLEM_TWO("Problem 2",
            (whitePlayer, blackPlayer) -> new ChessProblemFactoryImpl().problemTwo(whitePlayer, blackPlayer)),

    /**
     * The first problem in the list.
     */
    PROBLEM_THREE("Problem 3",
            (whitePlayer, blackPlayer) -> new ChessProblemFactoryImpl().problemThree(whitePlayer, blackPlayer)),

    /**
     * The first problem in the list.
     */
    PROBLEM_FOUR("Problem 4",
            (whitePlayer, blackPlayer) -> new ChessProblemFactoryImpl().problemFour(whitePlayer, blackPlayer));

    private final String name;
    private final BiFunction<Player, Player, ChessProblem> chessProblemFunction;

    ChessProblemsEnum(final String name, final BiFunction<Player, Player, ChessProblem> chessProblem) {
        this.name = name;
        this.chessProblemFunction = chessProblem;
    }

    public ChessProblem getChessProblem(final Player whitePlayer, final Player blackPlayer) {
        return this.chessProblemFunction.apply(whitePlayer, blackPlayer);
    }

    public String getName() {
        return this.name;
    }

}
