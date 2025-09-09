package shiroha.ui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.Node;

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

    public static DialogBox initialiseDialogBox(String s, Image i, DigalogType type) {
       
        assert s.length() < 100;

        var db = new DialogBox(s, i);

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