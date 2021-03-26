package jhaturanga.commons.graphics;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public final class EndGamePopup extends StackPane {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 200;
    private final Text message;
    private final Button button;
    private final VBox layout = new VBox();
    private final Stage primaryStage;

    public EndGamePopup() {
        this.primaryStage = new Stage();
        this.primaryStage.setScene(new Scene(this));
        this.setPrefWidth(WIDTH);
        this.setPrefHeight(HEIGHT);
        this.message = new Text();
        this.message.setFill(Color.BLACK);
        this.button = new Button();
        // TODO: IMPLEMENT
        // this.primaryStage.getScene().getStylesheets().add(ApplicationStyle.getApplicationStylePath());
        this.button.setText("EXIT"); // default
        this.layout.setAlignment(Pos.CENTER);
        this.layout.getChildren().addAll(this.message, this.button);
        this.getChildren().addAll(this.layout);
    }

    public void show() {
        primaryStage.show();
    }

    public void setMessage(final String message) {
        this.message.setText(message);
    }

    public void setButtonText(final String text) {
        this.button.setText(text);
    }

    public void close() {
        this.primaryStage.close();
    }

    public void setButtonAction(final Runnable function) {
        this.button.setOnMouseClicked(e -> {
            function.run();
        });
    }

}
