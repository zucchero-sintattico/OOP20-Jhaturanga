package jhaturanga.commons.graphics;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.movement.MovementImpl;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.piece.Piece;
import jhaturanga.views.editor.PieceRectangleImpl;

public final class GraphicPieceMovementStrategyImpl implements GraphicPieceMovementStrategy {

    private final MatchBoardView matchView;
    private final GridPane grid;
    private final boolean isWhiteBottom;
    private final boolean isOnline;

    private boolean isPieceBeingDragged;

    public GraphicPieceMovementStrategyImpl(final MatchBoardView matchView, final GridPane grid,
            final boolean isWhiteBottom, final boolean isOnline) {
        this.matchView = matchView;
        this.grid = grid;
        this.isWhiteBottom = isWhiteBottom;
        this.isOnline = isOnline;
    }

    private void detachPieceFromGrid(final PieceRectangleImpl piece) {
        if (this.grid.getChildren().contains(piece)) {
            this.grid.getChildren().remove(piece);
            this.matchView.getChildren().add(piece);
        }
    }

    private boolean isLocalPlayerPiece(final Piece piece) {
        return this.isWhiteBottom ? this.matchView.getMatchController().getWhitePlayer().equals(piece.getPlayer())
                : this.matchView.getMatchController().getBlackPlayer().equals(piece.getPlayer());
    }

    @Override
    public void onPieceClicked(final MouseEvent event) {
        final PieceRectangleImpl piece = (PieceRectangleImpl) event.getSource();
        if (this.isOnline && !this.isLocalPlayerPiece(piece.getPiece())) {
            return;
        }
        this.matchView.getScene().setCursor(Cursor.OPEN_HAND);
        this.detachPieceFromGrid(piece);
        if (this.matchView.getMatchController().getPlayerTurn().equals(piece.getPiece().getPlayer())
                && this.matchView.isPieceMovable()) {
            this.matchView.drawPossibleDestinations(piece);
        }
    }

    @Override
    public void onPieceDragged(final MouseEvent event) {
        final PieceRectangleImpl piece = (PieceRectangleImpl) event.getSource();
        if (this.isOnline && !this.isLocalPlayerPiece(piece.getPiece())) {
            return;
        }
        this.matchView.getScene().setCursor(Cursor.CLOSED_HAND);
        if (this.matchView.isPieceMovable()) {
            this.isPieceBeingDragged = true;
            piece.setX(event.getX() - piece.getWidth() / 2);
            piece.setY(event.getY() - piece.getHeight() / 2);
        }
    }

    @Override
    public void onPieceReleased(final MouseEvent event) {
        final PieceRectangleImpl piece = (PieceRectangleImpl) event.getSource();
        if (this.isOnline && !this.isLocalPlayerPiece(piece.getPiece())) {
            return;
        }
        this.matchView.getScene().setCursor(Cursor.DEFAULT);
        final BoardPosition startingPos = piece.getPiece().getPiecePosition();
        final BoardPosition position = this.getBoardPositionsFromGridCoordinates(event.getSceneX(), event.getSceneY());
        final BoardPosition realPosition = this.getGridCoordinateFromBoardPosition(position);

        if (this.isPieceBeingDragged) {
            this.isPieceBeingDragged = false;
            final Piece movedPiece = piece.getPiece();
            final MovementResult result = this.matchView.getMatchController().move(movedPiece.getPiecePosition(),
                    position);

            if (!result.equals(MovementResult.INVALID_MOVE)) {
                this.matchView.getChildren().remove(piece);
                this.grid.add(piece, realPosition.getX(), realPosition.getY());
                this.matchView.onMovement(this.matchView.getMatchController().getBoardStatus(),
                        new MovementImpl(movedPiece, startingPos, position), result);
            } else {
                this.abortMove(piece);
            }

            this.grid.requestFocus();
            this.matchView.redraw(this.matchView.getMatchController().getBoardStatus());
            this.matchView.resetHightlightedPositions();

        } else {
            this.abortMove(piece);
        }

    }

    private BoardPosition getBoardPositionsFromGridCoordinates(final double x, final double y) {

        final TileImpl tile = this.grid.getChildren().stream().filter(i -> i instanceof TileImpl).map(i -> (TileImpl) i)
                .findAny().get();

        final double xMargin = this.matchView.localToScene(this.matchView.getBoundsInLocal()).getMinX();
        final double yMargin = this.matchView.localToScene(this.matchView.getBoundsInLocal()).getMinY();

        final int column = (int) (((x - xMargin)
                / (tile.getWidth() * this.matchView.getMatchController().getBoardStatus().getColumns()))
                * this.matchView.getMatchController().getBoardStatus().getColumns());

        int row = (int) (((y - yMargin)
                / (tile.getHeight() * this.matchView.getMatchController().getBoardStatus().getRows()))
                * this.matchView.getMatchController().getBoardStatus().getRows());

        if (this.isWhiteBottom) {
            row = this.matchView.getMatchController().getBoardStatus().getRows() - 1 - row;
        }

        return new BoardPositionImpl(column, row);
    }

    private BoardPosition getGridCoordinateFromBoardPosition(final BoardPosition position) {
        final int row = this.isWhiteBottom
                ? this.matchView.getMatchController().getBoardStatus().getRows() - 1 - position.getY()
                : position.getY();
        return new BoardPositionImpl(position.getX(), row);
    }

    private void abortMove(final PieceRectangleImpl piece) {
        final BoardPosition realPiecePosition = this
                .getGridCoordinateFromBoardPosition(piece.getPiece().getPiecePosition());
        this.matchView.getChildren().remove(piece);
        this.grid.add(piece, realPiecePosition.getX(), realPiecePosition.getY());
    }
}
