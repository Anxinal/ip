package shiroha.ui;
import java.util.Scanner;

import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI {

    private Scene mainPage;
    /**
     * Renders a message from the chatbot to the user with line breaks before and after the message
     * @param s The message to be rendered
     */
    public void renderChatBotMessage(String s){
        addLineBreak();
        System.out.println(s);
        addLineBreak();
    }
    public UI(){
         ScrollPane scrollPane = new ScrollPane();
         VBox dialogContainer = new VBox();
         scrollPane.setContent(dialogContainer);

         TextField userInput = new TextField();
         Button sendButton = new Button("Send");

         AnchorPane mainLayout = new AnchorPane();
         mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);
                 mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);
        
        this.mainPage = new Scene(mainLayout);
    }

    public Scene getScene(){
        return this.mainPage;
    }
    /**
     * Renders an error message to the user with line breaks before and after the message
     * @param err The exception containing the error message to be rendered
     */
    public void renderErrorMessage(Exception err){
        addLineBreak();
        System.out.println(err.getMessage());
        addLineBreak();
    }

    private void addLineBreak(){
        System.out.println("--------(-w-)---------");
    }
    /**
     * Gets the next line of input from the user
     * @return The next line of input from the user
     */
    public String getNextInput(){
        return "";
    }

    /**
     * Closes the scanner
     */
    public void close(){
        return;
    }

}
