package jhaturanga.model.chessproblems;

import java.util.function.Function;

import jhaturanga.model.player.PlayerPair;

public enum ChessProblemsEnum {
    /**
     * The first problem in the list.
     */
    PROBLEM_ONE("Problem 1", new ChessProblemFactoryImpl()::problemOne),

    /**
     * The second problem in the list.
     */
    PROBLEM_TWO("Problem 2", new ChessProblemFactoryImpl()::problemTwo),

    /**
     * The third problem in the list.
     */
    PROBLEM_THREE("Problem 3", new ChessProblemFactoryImpl()::problemThree),

    /**
     * The fourth problem in the list.
     */
    PROBLEM_FOUR("Problem 4", new ChessProblemFactoryImpl()::problemFour),

    /**
     * The fifth problem in the list.
     */
    PROBLEM_FIVE("Problem 5", new ChessProblemFactoryImpl()::problemFive);

    private final String name;
    private final Function<PlayerPair, ChessProblem> chessProblemFunction;

    ChessProblemsEnum(final String name, final Function<PlayerPair, ChessProblem> chessProblem) {
        this.name = name;
        this.chessProblemFunction = chessProblem;
    }

    public ChessProblem getChessProblem(final PlayerPair players) {
        return this.chessProblemFunction.apply(players);
    }

    public String getName() {
        return this.name;
    }

}
