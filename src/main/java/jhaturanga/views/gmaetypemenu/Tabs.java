package jhaturanga.views.gmaetypemenu;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableDoubleValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class Tabs extends VBox {

    private TextArea description;
    private Button button;

    public Tabs(final ObservableDoubleValue width, final ObservableDoubleValue height, final int numberOfTab) {

        this.description = new TextArea();
        this.button = new Button();
        this.description.setEditable(false);
        this.setPadding(new Insets(10));
        // this.setAlignment(Pos.BOTTOM_CENTER);

        this.prefWidthProperty().bind(Bindings.divide(width, numberOfTab / 2));
        this.prefHeightProperty().bind(Bindings.divide(height, numberOfTab / 2));
        if (numberOfTab % 2 != 0) {
            this.prefWidthProperty().bind(Bindings.divide(width, (numberOfTab / 2) + 1));
        }

        this.button.prefWidthProperty().bind(this.widthProperty());
        this.button.prefHeightProperty().bind(this.heightProperty().divide(5));
        this.description.prefHeightProperty().bind(this.heightProperty().divide(2));
        this.description.prefWidthProperty().bind(this.widthProperty());

        this.description.styleProperty()
                .bind(Bindings.concat("-fx-font-size: ", this.widthProperty().add(this.widthProperty()).divide(30)));

        this.button.styleProperty()
                .bind(Bindings.concat("-fx-font-size: ", this.widthProperty().add(this.widthProperty()).divide(30)));
        this.getChildren().addAll(description, button);

    }

    public void setButtonText(final String text) {
        button.setText(text);
    }

    public void setDescription(final String text) {
        description.setText(text);
    }
    
    public Button getButton() {
        return this.button;
        
    }

}
