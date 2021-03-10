package jhaturanga.views.gametypemenu;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableDoubleValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class Tabs extends VBox {

    private final TextArea description;
    private final Button button;

    public Tabs(final ObservableDoubleValue width, final ObservableDoubleValue heigth, final int numberOfTab) {

        this.description = new TextArea();
        this.button = new Button();
        this.description.setEditable(false);
        this.setPadding(new Insets(10));

        this.prefWidthProperty().bind(Bindings.divide(width, numberOfTab / 2));
        this.prefHeightProperty().bind(Bindings.divide(heigth, numberOfTab / 2));
        if (numberOfTab % 2 != 0) {
            this.prefWidthProperty().bind(Bindings.divide(width, numberOfTab / 2 + 1));
        }

        this.button.prefWidthProperty().bind(width);
        this.button.prefHeightProperty().bind(Bindings.divide(heigth, 5));
        this.description.prefHeightProperty().bind(Bindings.divide(heigth, 2));
        this.description.prefWidthProperty().bind(width);

        this.getChildren().addAll(description, button);

    }

    public final void setButtonText(final String text) {
        button.setText(text);
    }

    public final void setDescription(final String text) {
        description.setText(text);
    }

    public final Button getButton() {
        return this.button;

    }

}
