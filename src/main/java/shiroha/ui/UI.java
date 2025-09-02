package shiroha.ui;

import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import shiroha.ChatBot;

public class UI {

    private Scene mainPage;
    ScrollPane scrollPane;   
    VBox dialogContainer;
    ChatBot bot;

    private static final double HEIGHT = 600.0;
    private static final double WIDTH  = 400.0;
    private static final double HEIGHT_DIALOG = 535;
    private static final double WIDTH_DIALOG = 385;
    private static final String BOT_LOGO_PATH = "/images/Shiroha.jpg";
    private static final String USER_LOGO_PATH = "/images/User.png";
    private final Image userImage;
    private final Image botImage;

    /**
     * initialise the chatbot interface without any messages displayed.
     * The interface consists of a scrollable dialog container to display messages,
     * a text field for user input, and a send button.
     */
    public UI(ChatBot bot){

        // handle the chatbot instance for responding to user input
        this.bot = bot;

        // initialise the images for the user and the bot
        userImage  = new Image(this.getClass().getResourceAsStream(USER_LOGO_PATH));
        botImage = new Image(this.getClass().getResourceAsStream(BOT_LOGO_PATH));

        this.scrollPane = new ScrollPane();
        this.dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        TextField userInput = new TextField();
        Button sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);
                 mainLayout.setPrefSize(WIDTH, HEIGHT);

        scrollPane.setPrefSize(WIDTH_DIALOG, HEIGHT_DIALOG);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        sendButton.setOnMouseClicked((event) -> {
            String input = userInput.getText();
            renderUserMessage(input);
            handleNextInput(input);
            userInput.clear();
        });

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        this.mainPage = new Scene(mainLayout);
    }

    /**
     * directly returns the main scene of the chatbot interface after initialisation
     * @return the main scene of the chatbot interface
     */
    public Scene getScene(){
        return this.mainPage;
    }

    
    /**
     * Renders a message from the chatbot to the user with line breaks before and after the message
     * @param s The message to be rendered
     */
    public void renderChatBotMessage(String s){
        this.dialogContainer.getChildren().add(new DialogBox(s, botImage));
    }

    /**
     * Render UserInput message on the dialog container
     * @param input The message to be rendered
     */
    public void renderUserMessage(String input){
        this.dialogContainer.getChildren().add(new DialogBox(input, userImage));
    }

    /**
     * Renders an error message to the user with line breaks before and after the message
     * @param err The exception containing the error message to be rendered
     */
    public void renderErrorMessage(Exception err){
        addLineBreak();
        this.dialogContainer.getChildren().add(new DialogBox(err.getMessage(), botImage));
    }

    private void addLineBreak(){
        System.out.println("--------(-w-)---------");
    }
    /**
     * Handles the next line of input from the user
     * @return The next line of input from the user
     */
    public void handleNextInput(String input){
        bot.respond(input);
    }

    /**
     * Closes the scanner
     */
    public void close(){
        return;

    }

}
