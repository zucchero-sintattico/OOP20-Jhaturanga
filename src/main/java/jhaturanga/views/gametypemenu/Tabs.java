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
    private static final int RATIO_FACTOR_BUTTON = 5;
    private static final int RATIO_FACTOR_DESCRIPTION = 2;

    public Tabs(final ObservableDoubleValue width, final ObservableDoubleValue heigth, final int numberOfTab) {

        this.description = new TextArea();
        this.button = new Button();
        this.description.setEditable(false);
        this.setPadding(new Insets(10));

        this.prefHeightProperty().bind(Bindings.divide(heigth, numberOfTab / 2));
        if (numberOfTab % 2 != 0) {
            this.prefWidthProperty().bind(Bindings.divide(width, numberOfTab / 2 + 1));
        } else {
            this.prefWidthProperty().bind(Bindings.divide(width, numberOfTab / 2));
        }

        this.button.prefWidthProperty().bind(this.widthProperty());
        this.button.prefHeightProperty().bind(Bindings.divide(this.heightProperty(), RATIO_FACTOR_BUTTON));
        this.description.prefHeightProperty().bind(Bindings.divide(this.heightProperty(), RATIO_FACTOR_DESCRIPTION));
        this.description.prefWidthProperty().bind(this.widthProperty());

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
