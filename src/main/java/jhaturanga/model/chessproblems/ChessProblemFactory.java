package jhaturanga.model.chessproblems;

import jhaturanga.model.player.Player;

public interface ChessProblemFactory {
    /**
     * @param whitePlayer - the white player of the problem
     * @param blackPlayer - the black player of the problem
     * @return ChessProblem - the chessProblem required.
     */
    ChessProblem problemOne(Player whitePlayer, Player blackPlayer);

    /**
     * @param whitePlayer - the white player of the problem
     * @param blackPlayer - the black player of the problem
     * @return ChessProblem - the chessProblem required.
     */
    ChessProblem problemTwo(Player whitePlayer, Player blackPlayer);

    /**
     * @param whitePlayer - the white player of the problem
     * @param blackPlayer - the black player of the problem
     * @return ChessProblem - the chessProblem required.
     */
    ChessProblem problemThree(Player whitePlayer, Player blackPlayer);

    /**
     * @param whitePlayer - the white player of the problem
     * @param blackPlayer - the black player of the problem
     * @return ChessProblem - the chessProblem required.
     */
    ChessProblem problemFour(Player whitePlayer, Player blackPlayer);

}
