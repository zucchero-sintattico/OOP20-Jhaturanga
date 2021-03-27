package jhaturanga.commons.graphics;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import jhaturanga.model.board.BoardPosition;

public class TileImpl extends Pane implements Tile {

    private final BoardPosition boardPosition;
    private final String baseColorStyle;
    private static final String PIECE_MOVEMENT_HIGHLIGHT_BASE_COLOR = "-fx-background-color:#FFE57C;";
    private String strokeStyle = "";
    private CircleHighlightImpl circle;
    private boolean isLastMovementHighlighted;

    public TileImpl(final BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
        this.setUpListeners();
        this.baseColorStyle = (this.boardPosition.getX() + this.boardPosition.getY()) % 2 == 0
                ? "-fx-background-color:#333;"
                : "-fx-background-color:#CCC;";
        this.setStyle(this.baseColorStyle);
        this.setPadding(new Insets(0));
    }

    private void setUpListeners() {
        this.setOnMouseEntered(e -> {
            if (!this.isLastMovementHighlighted) {
                if (this.circle != null) {
                    this.circle.onMouseEntered();
                    this.strokeStyle = "-fx-border-color: green; -fx-border-radius: 15.0; -fx-border-width: 5";
                } else {
                    this.strokeStyle = "-fx-border-color: black; -fx-border-radius: 15.0;";
                }
                this.setStyle(this.baseColorStyle + this.strokeStyle);
            }
        });

        this.setOnMouseExited(e -> {
            if (!this.isLastMovementHighlighted) {
                if (this.circle != null) {
                    this.circle.onMouseExited();
                }
                this.strokeStyle = "-fx-border-color: transparent;";
                this.setStyle(this.baseColorStyle + this.strokeStyle);
            }
        });
    }

    @Override
    public final void highlightPosition(final boolean isPiecePresent) {
        this.circle = new CircleHighlightImpl(this, isPiecePresent);
        this.getChildren().add(this.circle);
    }

    @Override
    public final void resetHighlightPosition() {
        Optional.ofNullable(this.circle).ifPresent(this.getChildren()::remove);
        this.circle = null;
    }

    @Override
    public final BoardPosition getBoardPosition() {
        return this.boardPosition;
    }

    @Override
    public final void highlightMovement() {
        this.isLastMovementHighlighted = true;
        this.setStyle(PIECE_MOVEMENT_HIGHLIGHT_BASE_COLOR + this.strokeStyle);
    }

    @Override
    public final void resetMovementHighlight() {
        this.isLastMovementHighlighted = false;
        this.setStyle(this.baseColorStyle + this.strokeStyle);
    }

}
