package jhaturanga.model.problems;

import java.util.function.Function;

import jhaturanga.model.player.PlayerPair;

public enum Problems {
    /**
     * The first problem in the list.
     */
    PROBLEM_ONE("Problem 1", new ProblemFactoryImpl()::problemOne),

    /**
     * The second problem in the list.
     */
    PROBLEM_TWO("Problem 2", new ProblemFactoryImpl()::problemTwo),

    /**
     * The third problem in the list.
     */
    PROBLEM_THREE("Problem 3", new ProblemFactoryImpl()::problemThree),

    /**
     * The fourth problem in the list.
     */
    PROBLEM_FOUR("Problem 4", new ProblemFactoryImpl()::problemFour),

    /**
     * The fifth problem in the list.
     */
    PROBLEM_FIVE("Problem 5", new ProblemFactoryImpl()::problemFive);

    private final String name;
    private final Function<PlayerPair, Problem> chessProblemFunction;

    Problems(final String name, final Function<PlayerPair, Problem> chessProblem) {
        this.name = name;
        this.chessProblemFunction = chessProblem;
    }

    public Problem getChessProblem(final PlayerPair players) {
        return this.chessProblemFunction.apply(players);
    }

    public String getName() {
        return this.name;
    }

}
