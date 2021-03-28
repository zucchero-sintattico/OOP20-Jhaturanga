package jhaturanga.commons.graphics.components;

import jhaturanga.model.board.BoardPosition;

public interface Tile {

    /**
     * Use this method when you want to add a CircleHighlight to the Tile.
     * 
     * @param isPiecePresent - is piece present
     */
    void highlightPosition(boolean isPiecePresent);

    /**
     * Call this method to clean and reset the Tile from the circle.
     */
    void resetHighlightPosition();

    /**
     * Call this method to reset the Tile from the lastMovement highlighting.
     */
    void resetHighlightMovement();

    /**
     * Use this method to know what BoardPosition the Tile represents.
     * 
     * @return BoardPosition representing the Tile's BoardPosition in the match.
     */
    BoardPosition getBoardPosition();

    /**
     * Use this method to change the Tile's color to highlight movement.
     * 
     */
    void highlightMovement();
}
