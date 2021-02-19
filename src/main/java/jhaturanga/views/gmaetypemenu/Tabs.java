package jhaturanga.views.gmaetypemenu;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableDoubleValue;
import javafx.scene.control.Button;

public class Tabs extends Button {

    public Tabs(final ObservableDoubleValue width, final ObservableDoubleValue height, final int numberOfTab) {
        this.prefWidthProperty().bind(Bindings.divide(width, numberOfTab / 2));
        this.prefHeightProperty().bind(Bindings.divide(height, numberOfTab / 2));
        if (numberOfTab % 2 != 0) {
            this.prefWidthProperty().bind(Bindings.divide(width, (numberOfTab / 2) + 1));
        }

        this.styleProperty()
                .bind(Bindings.concat("-fx-font-size: ", this.widthProperty().add(this.widthProperty()).divide(40)));

    }

}
