package jhaturanga.views.match;

import java.util.Optional;

import javafx.scene.layout.Pane;
import jhaturanga.model.board.BoardPosition;

public class TileImpl extends Pane implements Tile {
    private final BoardPosition boardPosition;
    private final String baseColorStyle;
    private static final String PIECE_MOVEMENT_HIGHLIGHT_BASE_COLOR = "-fx-background-color:#FFE57C;";
    private String strokeStyle = "";
    private Optional<CircleHighlightImpl> circle;
    private boolean isLastMovementHighlighted;

    public TileImpl(final BoardPosition boardPosition) {
        this.circle = Optional.empty();
        this.boardPosition = boardPosition;
        this.setUpListeners();
        this.baseColorStyle = (this.boardPosition.getX() + this.boardPosition.getY()) % 2 == 0
                ? "-fx-background-color:#333;"
                : "-fx-background-color:#CCC;";
        this.setStyle(this.baseColorStyle);
    }

    private void setUpListeners() {
        this.setOnMouseEntered(e -> {
            if (!this.isLastMovementHighlighted) {
                if (this.circle.isPresent()) {
                    this.circle.get().onMouseEntered();
                    this.strokeStyle = "-fx-border-color: green; -fx-border-radius: 15.0; -fx-border-width: 5";
                } else {
                    this.strokeStyle = "-fx-border-color: black; -fx-border-radius: 15.0;";
                }
                this.setStyle(this.baseColorStyle + this.strokeStyle);
            }
        });
        this.setOnMouseExited(e -> {
            if (!this.isLastMovementHighlighted) {
                if (this.circle.isPresent()) {
                    this.circle.get().onMouseExited();
                }
                this.strokeStyle = "-fx-border-color: transparent;";
                this.setStyle(this.baseColorStyle + this.strokeStyle);
            }
        });
    }

    @Override
    public final void addCircleHighlight(final CircleHighlightImpl circle) {
        this.circle = Optional.of(circle);
        this.getChildren().add(this.circle.get());
    }

    @Override
    public final void resetCircleHighlight() {
        this.circle = Optional.empty();
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
