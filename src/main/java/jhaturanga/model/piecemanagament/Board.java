package jhaturanga.model.piecemanagament;

import java.util.Set;

public interface Board {
    /**
     * 
     * @return the state of the Match's Board as a set of Piece's
     * 
     */
    Set<Piece> getBoardState();
}
