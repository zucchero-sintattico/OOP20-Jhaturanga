package jhaturanga.model.board.starting;

import jhaturanga.model.board.Board;
import jhaturanga.model.player.pair.PlayerPair;

public interface StartingBoardFactory {
    /**
     * Use this method to get a starting classic Board.
     * 
     * @param players
     * @return Board representing the starting board wanted
     */
    Board classicBoard(PlayerPair players);

    /**
     * Use this method to get a starting pawn horde Board.
     * 
     * @param players
     * @return Board representing the starting board wanted
     */
    Board pawnHordeBoard(PlayerPair players);

    /**
     * Use this method to get a starting three columns Board.
     * 
     * @param players
     * @return Board representing the starting board wanted
     */
    Board threeColumnsBoard(PlayerPair players);

    /**
     * Use this method to get a starting Board to play 1D chess.
     * 
     * @param players
     * @return Board representing the starting board wanted
     */
    Board oneDimensionBoard(PlayerPair players);

    /**
     * Use this method to get a customized Board.
     * 
     * @param players
     * @param columns       - number of columns of the custom starting board.
     * @param rows          - the number of rows of the custom starting board
     * @param startingBoard - the starting board wanted
     * @return Board representing the starting board wanted
     */
    Board customizedBoard(String startingBoard, int columns, int rows, PlayerPair players);

    /**
     * Use this method to get a customized Board.
     * 
     * @param players
     * @return Board representing the starting board wanted
     */
    Board problemOneBoard(PlayerPair players);

    /**
     * Use this method to get a customized Board.
     * 
     * @param players
     * @return Board representing the starting board wanted
     */
    Board problemTwoBoard(PlayerPair players);

    /**
     * Use this method to get a customized Board.
     * 
     * @param players
     * @return Board representing the starting board wanted
     */
    Board problemThreeBoard(PlayerPair players);

    /**
     * Use this method to get a customized Board.
     * 
     * @param players
     * 
     * @return Board representing the starting board wanted
     */
    Board problemFourBoard(PlayerPair players);

    /**
     * Use this method to get a customized Board.
     * 
     * @param players
     * 
     * @return Board representing the starting board wanted
     */
    Board problemFiveBoard(PlayerPair players);

}
