package shiroha.ui;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class DialogBox extends HBox {

    private Label text;
    private ImageView displayPicture;
    private static final double FIT_WIDTH = 110.0;
    private static final double FIT_HEIGHT = 110.0;
    public DialogBox(String s, Image i) {

        text = new Label(s);
        displayPicture = new ImageView(i);
        //Styling the dialog box
        text.setWrapText(true);
        displayPicture.setFitWidth(FIT_WIDTH);
        displayPicture.setFitHeight(FIT_HEIGHT);
        this.setAlignment(Pos.TOP_RIGHT);
        this.getChildren().addAll(text, displayPicture);
        HBox.setMargin(displayPicture, getInsets());
    }

}