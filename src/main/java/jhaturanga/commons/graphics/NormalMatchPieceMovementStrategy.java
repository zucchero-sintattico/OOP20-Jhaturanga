package jhaturanga.commons.graphics;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.match.MatchStatusEnum;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.piece.Piece;
import jhaturanga.views.editor.PieceRectangleImpl;

public class NormalMatchPieceMovementStrategy implements GraphicPieceMovementStrategy {

    private final MatchBoard board;

    private boolean isPieceBeingDragged;

    public NormalMatchPieceMovementStrategy(final MatchBoard board) {
        this.board = board;
    }

    private void detachPieceFromGrid(final PieceRectangleImpl piece) {
        if (this.board.getGrid().getChildren().contains(piece)) {
            this.board.getGrid().getChildren().remove(piece);
            this.board.getChildren().add(piece);
        }
    }

    /**
     * 
     */
    @Override
    public void onPieceClicked(final MouseEvent event) {
        final PieceRectangleImpl piece = (PieceRectangleImpl) event.getSource();
        if (this.isPieceMovable()) {
            this.board.getScene().setCursor(Cursor.OPEN_HAND);
            this.detachPieceFromGrid(piece);
            if (this.board.getMatchController().getPlayerTurn().equals(piece.getPiece().getPlayer())) {
                this.board.drawPossibleDestinations(piece);
            }
        }

    }

    /**
     * 
     */
    @Override
    public void onPieceDragged(final MouseEvent event) {
        final PieceRectangleImpl piece = (PieceRectangleImpl) event.getSource();
        if (this.isPieceMovable()) {
            this.board.getScene().setCursor(Cursor.CLOSED_HAND);
            this.isPieceBeingDragged = true;
            piece.setX(event.getX() - piece.getWidth() / 2);
            piece.setY(event.getY() - piece.getHeight() / 2);
        }
    }

    /**
     * 
     */
    @Override
    public void onPieceReleased(final MouseEvent event) {
        final PieceRectangleImpl piece = (PieceRectangleImpl) event.getSource();
        this.board.getScene().setCursor(Cursor.DEFAULT);
        final BoardPosition startingPos = piece.getPiece().getPiecePosition();
        final BoardPosition position = this.getBoardPositionsFromGridCoordinates(event.getSceneX(), event.getSceneY());
        final BoardPosition realPosition = this.getGridCoordinateFromBoardPosition(position);

        if (this.isPieceBeingDragged) {
            this.isPieceBeingDragged = false;
            final Piece movedPiece = piece.getPiece();
            final MovementResult result = this.board.getMatchController().move(movedPiece.getPiecePosition(), position);

            if (!result.equals(MovementResult.INVALID_MOVE)) {
                this.board.getChildren().remove(piece);
                this.board.getGrid().add(piece, realPosition.getX(), realPosition.getY());
                this.board.onMovement(this.board.getMatchController().getBoardStatus(),
                        new MovementImpl(movedPiece, startingPos, position), result);
            } else {
                this.abortMove(piece);
            }

            this.board.getGrid().requestFocus();

        } else {
            this.abortMove(piece);
        }

    }

    private boolean isPieceMovable() {
        return !this.board.getMatchController().isInNavigationMode()
                && this.board.getMatchController().matchStatus().equals(MatchStatusEnum.ACTIVE);
    }

    /**
     * 
     * @param x
     * @param y
     * @return the position
     */
    public BoardPosition getBoardPositionsFromGridCoordinates(final double x, final double y) {

        final TileImpl tile = this.board.getGrid().getChildren().stream().filter(i -> i instanceof TileImpl)
                .map(i -> (TileImpl) i).findAny().get();

        final double xMargin = this.board.localToScene(this.board.getBoundsInLocal()).getMinX();
        final double yMargin = this.board.localToScene(this.board.getBoundsInLocal()).getMinY();

        final int column = (int) (((x - xMargin)
                / (tile.getWidth() * this.board.getMatchController().getBoardStatus().getColumns()))
                * this.board.getMatchController().getBoardStatus().getColumns());

        int row = (int) (((y - yMargin)
                / (tile.getHeight() * this.board.getMatchController().getBoardStatus().getRows()))
                * this.board.getMatchController().getBoardStatus().getRows());
        row = this.board.getMatchController().getBoardStatus().getRows() - 1 - row;

        return new BoardPositionImpl(column, row);
    }

    /**
     * 
     * @param position
     * @return the position
     */
    public BoardPosition getGridCoordinateFromBoardPosition(final BoardPosition position) {
        final int row = this.board.getMatchController().getBoardStatus().getRows() - 1 - position.getY();
        return new BoardPositionImpl(position.getX(), row);
    }

    private void abortMove(final PieceRectangleImpl piece) {
        final BoardPosition realPiecePosition = this
                .getGridCoordinateFromBoardPosition(piece.getPiece().getPiecePosition());
        this.board.getChildren().remove(piece);
        this.board.getGrid().add(piece, realPiecePosition.getX(), realPiecePosition.getY());
    }
}
