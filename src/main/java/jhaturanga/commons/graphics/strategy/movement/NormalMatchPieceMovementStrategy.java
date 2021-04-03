package jhaturanga.commons.graphics.strategy.movement;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import jhaturanga.commons.graphics.board.MatchBoard;
import jhaturanga.commons.graphics.components.PieceRectangle;
import jhaturanga.commons.graphics.components.Tile;
import jhaturanga.controllers.match.MatchController;
import jhaturanga.model.board.BoardPosition;
import jhaturanga.model.board.BoardPositionImpl;
import jhaturanga.model.match.MatchStatus;
import jhaturanga.model.movement.MovementResult;
import jhaturanga.model.movement.PieceMovementImpl;
import jhaturanga.model.piece.Piece;

public class NormalMatchPieceMovementStrategy implements GraphicPieceMovementStrategy {

    private final MatchController controller;
    private final MatchBoard board;

    private boolean isPieceBeingDragged;

    public NormalMatchPieceMovementStrategy(final MatchBoard board, final MatchController controller) {
        this.board = board;
        this.controller = controller;
    }

    private void detachPieceFromGrid(final PieceRectangle piece) {
        if (this.board.getGrid().getChildren().contains(piece)) {
            this.board.getGrid().getChildren().remove(piece);
            this.board.getChildren().add(piece);
        }
    }

    /**
     * 
     */
    @Override
    public void onPiecePressed(final MouseEvent event) {
        final PieceRectangle piece = (PieceRectangle) event.getSource();
        if (this.isPieceMovable()) {
            this.board.getScene().setCursor(Cursor.OPEN_HAND);
            this.detachPieceFromGrid(piece);
            if (this.getMatchController().getPlayerTurn().equals(piece.getPiece().getPlayer())) {
                this.board.drawPossibleDestinations(piece.getPiece());
            }
        }

    }

    /**
     * 
     */
    @Override
    public void onPieceDragged(final MouseEvent event) {
        final PieceRectangle piece = (PieceRectangle) event.getSource();
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
        final PieceRectangle piece = (PieceRectangle) event.getSource();
        if (this.isPieceMovable()) {
            this.board.getScene().setCursor(Cursor.DEFAULT);
            final BoardPosition startingPos = piece.getPiece().getPiecePosition();
            final BoardPosition position = this.getBoardPositionsFromGridCoordinates(event.getSceneX(),
                    event.getSceneY());
            final BoardPosition realPosition = this.getGridCoordinateFromBoardPosition(position);

            if (this.isPieceBeingDragged) {
                this.isPieceBeingDragged = false;
                final Piece movedPiece = piece.getPiece();
                final MovementResult result = this.getMatchController().move(movedPiece.getPiecePosition(), position);

                if (!result.equals(MovementResult.INVALID_MOVE)) {
                    this.board.getChildren().remove(piece);
                    this.board.getGrid().add(piece, realPosition.getX(), realPosition.getY());
                    this.board.onMovement(this.getMatchController().getBoardStatus(),
                            new PieceMovementImpl(movedPiece, startingPos, position), result);
                } else {
                    this.abortMove(piece);
                }

            } else {
                this.abortMove(piece);
            }

        }

        this.board.getGrid().requestFocus();

    }

    private boolean isPieceMovable() {
        return !this.getMatchController().isInNavigationMode()
                && !this.getMatchController().getMatchStatus().equals(MatchStatus.ENDED);
    }

    /**
     * 
     * @param x
     * @param y
     * @return the position
     */
    public BoardPosition getBoardPositionsFromGridCoordinates(final double x, final double y) {

        final Tile tile = this.board.getGrid().getChildren().stream().filter(i -> i instanceof Tile).map(i -> (Tile) i)
                .findAny().get();

        final double xMargin = this.board.localToScene(this.board.getBoundsInLocal()).getMinX();
        final double yMargin = this.board.localToScene(this.board.getBoundsInLocal()).getMinY();

        final int column = (int) ((((x - xMargin) / this.board.getScene().getRoot().getScaleX())
                / (tile.getWidth() * this.getMatchController().getBoardStatus().getColumns()))
                * this.getMatchController().getBoardStatus().getColumns());

        int row = (int) ((((y - yMargin) / this.board.getScene().getRoot().getScaleY())
                / (tile.getHeight() * this.getMatchController().getBoardStatus().getRows()))
                * this.getMatchController().getBoardStatus().getRows());
        row = this.getMatchController().getBoardStatus().getRows() - 1 - row;

        return new BoardPositionImpl(column, row);
    }

    /**
     * 
     * @param position
     * @return the position
     */
    public BoardPosition getGridCoordinateFromBoardPosition(final BoardPosition position) {
        final int row = this.getMatchController().getBoardStatus().getRows() - 1 - position.getY();
        return new BoardPositionImpl(position.getX(), row);
    }

    private void abortMove(final PieceRectangle piece) {
        final BoardPosition realPiecePosition = this
                .getGridCoordinateFromBoardPosition(piece.getPiece().getPiecePosition());
        this.board.getChildren().remove(piece);
        this.board.getGrid().add(piece, realPiecePosition.getX(), realPiecePosition.getY());
    }

    /**
     * 
     * @return the match controller
     */
    protected MatchController getMatchController() {
        return this.controller;
    }
}
