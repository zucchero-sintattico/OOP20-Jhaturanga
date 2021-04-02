package jhaturanga.model.chessproblems;

import jhaturanga.commons.PlayerPair;

public interface ChessProblemFactory {
    /**
     * @param players
     * @return ChessProblem - the chessProblem required.
     */
    ChessProblem problemOne(PlayerPair players);

    /**
     * @param players
     * @return ChessProblem - the chessProblem required.
     */
    ChessProblem problemTwo(PlayerPair players);

    /**
     * @param players
     * @return ChessProblem - the chessProblem required.
     */
    ChessProblem problemThree(PlayerPair players);

    /**
     * @param players
     * @return ChessProblem - the chessProblem required.
     */
    ChessProblem problemFour(PlayerPair players);

    /**
     * @param players
     * @return ChessProblem - the chessProblem required.
     */
    ChessProblem problemFive(PlayerPair players);
}
