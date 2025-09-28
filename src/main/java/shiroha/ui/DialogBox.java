package shiroha.ui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class DialogBox extends HBox {

    public enum DigalogType {
        NORMAL,
        USER,
        ERROR,
    }

    private Label text;
    private ImageView displayPicture;
    private static final double FIT_WIDTH = 110.0;
    private static final double FIT_HEIGHT = 110.0;

    private DialogBox(String s, Image i) {

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
    /** 
     * A factory method that initialises a dialog box with the specified type
     * @param message Message to be displayed
     * @param image The user/chatbot Image to be displayed
     * @param type The type of dialog box to be displayed
     * @return A dialog box with the specified type
     */
    public static DialogBox initialiseDialogBox(String message, Image image, DigalogType type) {

        var db = new DialogBox(message, image);
        switch (type) {
        case USER:
            db.setAlignment(Pos.TOP_LEFT);
            ObservableList<Node> tmp = FXCollections.observableArrayList(db.getChildren());
            FXCollections.reverse(tmp);
            db.getChildren().setAll(tmp);
            break;
        case ERROR:
            db.setStyle("-fx-background-color: #ffcccc; -fx-border-color: red; -fx-border-width: 2px;");
            break;
        default:
            break;
        }
        return db;
    }


}