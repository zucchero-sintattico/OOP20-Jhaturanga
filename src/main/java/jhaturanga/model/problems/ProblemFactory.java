package jhaturanga.model.problems;

import jhaturanga.model.player.pair.PlayerPair;

public interface ProblemFactory {
    /**
     * @param players
     * @return ChessProblem - the chessProblem required.
     */
    Problem problemOne(PlayerPair players);

    /**
     * @param players
     * @return ChessProblem - the chessProblem required.
     */
    Problem problemTwo(PlayerPair players);

    /**
     * @param players
     * @return ChessProblem - the chessProblem required.
     */
    Problem problemThree(PlayerPair players);

    /**
     * @param players
     * @return ChessProblem - the chessProblem required.
     */
    Problem problemFour(PlayerPair players);

    /**
     * @param players
     * @return ChessProblem - the chessProblem required.
     */
    Problem problemFive(PlayerPair players);
}
