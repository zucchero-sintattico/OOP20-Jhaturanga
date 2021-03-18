package jhaturanga.views.gametypemenu;

import org.w3c.dom.events.EventException;

import com.sun.jdi.event.Event;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableDoubleValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class TabImpl extends VBox implements Tab {

    private final TextArea description;
    private final Button button;
    private static final int RATIO_FACTOR_BUTTON = 5;
    private static final int RATIO_FACTOR_DESCRIPTION = 2;
    private static final int TEXT_SIZE = 13;

    public TabImpl(final ObservableDoubleValue width, final ObservableDoubleValue heigth, final int numberOfTab) {

        this.description = new TextArea();
        this.description.setWrapText(true);
        this.description.setFont(Font.font("verdana", FontWeight.MEDIUM, FontPosture.REGULAR, TEXT_SIZE));
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

    @Override
    public final void setButtonText(final String text) {
        button.setText(text);
    }

    @Override
    public final void setDescription(final String text) {
        description.setText(text);
    }

    @Override
    public void setButtonAction(EventHandler<ActionEvent> event) {
        this.button.setOnAction(event);
        
    }



}
