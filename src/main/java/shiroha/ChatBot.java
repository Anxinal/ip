package shiroha;

import shiroha.command.Command;
import shiroha.command.Parser;
import shiroha.exceptions.UnknownCommandException;
import shiroha.ui.UI;
import javafx.scene.Scene;
public class ChatBot {

    private static final String NAME = "SHIROHA"; 
    private static final String LOGO = "Chatbot - Shiroha XD";
    private Storage store;
    private UI ui;
    private Parser parser;
    private static final String DEFAULT_MEMO_PATH = "./data/ShirohaTaskMemory.mem";
    public ChatBot(){
        store = Storage.initialiseStorage(DEFAULT_MEMO_PATH);
        ui = new UI(this);
        parser = new Parser(store.readTaskList());
    }
    /**
     * Starts the chatbot, greets the user, and enters a loop to process user input until "bye" is received
     */
    public Scene getUiComponent(){
        return this.ui.getScene();
    }

    /**
     * returns the name of the chatbot
     * @return the name of the chatbot
     */
    public String getName(){
        return ChatBot.NAME;
    }

    public void start(){
        greet();
        
        // while(true){
        //     try{
        //         String next = this.ui.getNextInput();
        //         if(next.equals("bye")){
        //             exit();
        //             break;
        //         }
        //         Command nextAction = parser.parse(next);
        //         ui.renderChatBotMessage(nextAction.action());

        //     }catch(UnknownCommandException e){
        //         ui.renderErrorMessage(e);
        //     }

        // }

        //this.store.writeTaskList();
       // this.ui.close();        
    }

    public void respond(String input){
        try{
            if(input.equals("bye")){
                exit();
                //this.ui.close();
                ui.renderChatBotMessage("See you.");
            }
            Command nextAction = parser.parse(input);
            ui.renderChatBotMessage(nextAction.action());

        }catch(UnknownCommandException e){
            ui.renderErrorMessage(e);
        }
    }

    /**
     * Greets the user with a welcome message
     */
    private void greet(){
        ui.renderChatBotMessage("Hello I am your Chatbot "+ ChatBot.NAME 
        + " \n Anything in your mind right now? Want to walk by the sea?");

    }
    /**
     * Display the goodbye message
     */
    private void exit(){
        ui.renderChatBotMessage("See you.");
    }

    


}
