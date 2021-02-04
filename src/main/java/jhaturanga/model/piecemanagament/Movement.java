package jhaturanga.model.piecemanagament;

import java.util.Map;
import java.util.Set;

public interface Movement {
    /**
     * @return a set of the Pieces involved in the movement(we need to return a Set
     *         because a Movement may apply a positional change to more than one
     *         Piece on the Board)
     */
    Set<Piece> getPiecesInvolved();

    /**
     * @return a Map containing as Keys the Piece's involved and as Value it's
     *         starting BoardPosition
     */
    Map<Piece, BoardPosition> getOrigin();

    /**
     * @return a Map containing as Keys the Piece's involved and as Value it's
     *         destination BoardPosition
     */
    Map<Piece, BoardPosition> getDestination();
}
