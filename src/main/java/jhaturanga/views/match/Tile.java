package jhaturanga.views.match;

import java.util.Optional;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import jhaturanga.model.board.BoardPosition;

public class Tile extends Pane {
    private final BoardPosition boardPosition;
    private String baseColorStyle;
    private String strokeStyle;
    private Optional<CircleHighlight> circle;

    public Tile(final BoardPosition boardPosition) {
        this.circle = Optional.empty();
        this.boardPosition = boardPosition;
        this.setUpListeners();
        this.baseColorStyle = (this.boardPosition.getX() + this.boardPosition.getY()) % 2 == 0
                ? "-fx-background-color:#CCC;"
                : "-fx-background-color:#333;";
        this.setStyle(this.baseColorStyle);
    }

    public final void addCircleHighlight(final CircleHighlight circle) {
        this.circle = Optional.of(circle);
        this.getChildren().add(this.circle.get());
    }

    public final void resetCircleHighlight() {
        this.circle = Optional.empty();
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

    public final BoardPosition getBoardPosition() {
        return this.boardPosition;
    }

}
