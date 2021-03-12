package jhaturanga.views.match;

import java.util.Optional;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import jhaturanga.model.board.BoardPosition;

public class TileImpl extends Pane implements Tile {
    private final BoardPosition boardPosition;
    private String baseColorStyle;
    private String strokeStyle;
    private Optional<CircleHighlightImpl> circle;

    public TileImpl(final BoardPosition boardPosition) {
        this.circle = Optional.empty();
        this.boardPosition = boardPosition;
        this.setUpListeners();
        this.baseColorStyle = (this.boardPosition.getX() + this.boardPosition.getY()) % 2 == 0
                ? "-fx-background-color:#CCC;"
                : "-fx-background-color:#333;";
        this.setStyle(this.baseColorStyle);
    }

    private void setUpListeners() {
        this.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.SECONDARY)) {
                if ("-fx-background-color:#00FF00;".equals(this.getStyle())) {
                    this.baseColorStyle = (this.boardPosition.getX() + this.boardPosition.getY()) % 2 == 0
                            ? "-fx-background-color:#CCC;"
                            : "-fx-background-color:#333;";
                    this.setStyle(this.baseColorStyle);
                } else {
                    this.baseColorStyle = "-fx-background-color:#00FF00;";
                    this.setStyle(this.baseColorStyle);
                }
            }
        });
        this.setOnMouseEntered(e -> {
            if (this.circle.isPresent()) {
                this.circle.get().onMouseEntered();
                this.strokeStyle = "-fx-border-color: green; -fx-border-radius: 15.0; -fx-border-width: 5";
            } else {
                this.strokeStyle = "-fx-border-color: black; -fx-border-radius: 15.0;";
            }

            this.setStyle(this.baseColorStyle + this.strokeStyle);
        });
        this.setOnMouseExited(e -> {
            if (this.circle.isPresent()) {
                this.circle.get().onMouseExited();
            }
            this.strokeStyle = "-fx-border-color: transparent;";
            this.setStyle(this.baseColorStyle + this.strokeStyle);
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

}
