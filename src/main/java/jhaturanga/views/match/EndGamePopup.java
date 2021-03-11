package jhaturanga.views.match;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import javafx.scene.text.Text;
import javafx.stage.Stage;

public final class EndGamePopup extends Application {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 200;
    private final Text message = new Text();

    @Override
    public void start(final Stage primaryStage) {

        final StackPane layout = new StackPane();
        layout.setPrefWidth(WIDTH);
        layout.setPrefHeight(HEIGHT);
        this.message.setFill(Color.BLACK);
        layout.getChildren().add(this.message);

        primaryStage.setScene(new Scene(layout));
        primaryStage.show();
    }

    public void setMessage(final String message) {
        this.message.setText(message);
    }

}
