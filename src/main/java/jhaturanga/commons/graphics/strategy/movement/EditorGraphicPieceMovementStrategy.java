package jhaturanga.commons.graphics.strategy.movement;

import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jhaturanga.commons.graphics.board.EditorBoard;
import jhaturanga.commons.graphics.components.PieceRectangle;
import jhaturanga.controllers.editor.EditorController;

public final class EditorGraphicPieceMovementStrategy implements GraphicPieceMovementStrategy {

    private final EditorController controller;
    private final EditorBoard board;

    public EditorGraphicPieceMovementStrategy(final EditorBoard board, final EditorController controller) {
        this.board = board;
        this.controller = controller;
    }

    @Override
    public void onPiecePressed(final MouseEvent event) {
        final PieceRectangle pieceRect = (PieceRectangle) event.getSource();
        this.board.getScene().setCursor(Cursor.OPEN_HAND);
        // Check if it's over limit

        if (event.getButton().equals(MouseButton.SECONDARY) && this.board.isMouseOnBoard(event)) {
            this.board.removePieceTotally(pieceRect);
        } else {
            if (this.board.getGrid().getChildren().contains(pieceRect)) {
                if (event.getButton().equals(MouseButton.MIDDLE)) {
                    this.board.removePieceTotally(pieceRect);
                    this.board.drawPieceOnGuiBoard(event, pieceRect);
                } else {
                    this.board.getGrid().getChildren().remove(pieceRect);
                }
            } else {
                this.board.generateNewPieceViewPort(event, pieceRect);
            }
            this.board.getGrid().getChildren().add(pieceRect);
        }
    }

    @Override
    public void onPieceDragged(final MouseEvent event) {
        final PieceRectangle piece = (PieceRectangle) event.getSource();
        this.board.getScene().setCursor(Cursor.CLOSED_HAND);
        piece.setX(event.getX() - piece.getWidth() / 2);
        piece.setY(event.getY() - piece.getHeight() / 2);
        if (event.getButton().equals(MouseButton.PRIMARY) && event.isControlDown()) {
            this.board.drawPieceOnGuiBoard(event, piece);
            this.board.redraw(this.controller.getBoardStatus());
        } else if (event.getButton().equals(MouseButton.SECONDARY)) {
            this.board.getPieces().stream()
                    .filter(i -> i.getPiece().getPiecePosition().equals(
                            this.board.getBoardPositionsFromGuiCoordinates(event.getSceneX(), event.getSceneY())))
                    .findAny().ifPresent(e -> {
                        this.board.removePieceTotally(e);
                        this.board.redraw(this.controller.getBoardStatus());
                    });
        }
    }

    @Override
    public void onPieceReleased(final MouseEvent event) {
        final PieceRectangle piece = (PieceRectangle) event.getSource();
        this.board.getScene().setCursor(Cursor.DEFAULT);
        this.board.updatePiecePositionOnGuiBoard(event, piece);
        this.board.redraw(this.controller.getBoardStatus());
    }

}
