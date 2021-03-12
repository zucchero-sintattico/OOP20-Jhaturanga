package jhaturanga.views.match;

import jhaturanga.model.board.BoardPosition;

public interface Tile {
    /**
     * Use this method when you want to add a CircleHighlight to the Tile.
     * 
     * @param circle the circle to add to the Tile
     */
    void addCircleHighlight(CircleHighlightImpl circle);

    /**
     * Call this method to clean and reset the Tile from the circle.
     */
    void resetCircleHighlight();

    /**
     * Use this method to know what BoardPosition the Tile represents.
     * 
     * @return BoardPosition representing the Tile's BoardPosition in the match.
     */
    BoardPosition getBoardPosition();
}
