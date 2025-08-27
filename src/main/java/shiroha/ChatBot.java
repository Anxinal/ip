package shiroha;

import shiroha.command.Command;
import shiroha.command.Parser;
import shiroha.exceptions.UnknownCommandException;

public class ChatBot {

    private static final String name = "SHIROHA"; 
    private static final String logo = "Chatbot - Shiroha XD";
    private Storage store;
    private ParserTest ui;
    private Parser parser;
    private static final String DEFAULT_MEMO_PATH = "./data/ShirohaTaskMemory.mem";
    public ChatBot(){
        store = Storage.initialiseStorage(DEFAULT_MEMO_PATH);
        ui = new ParserTest();
        parser = new Parser(store.readTaskList());
    }
    /**
     * Starts the chatbot, greets the user, and enters a loop to process user input until "bye" is received
     */
    public void start(){
        greet();
        
        while(true){
            try{
                String next = this.ui.getNextInput();
                if(next.equals("bye")){
                    exit();
                    break;
                }
                Command nextAction = parser.parse(next);
                ui.renderChatBotMessage(nextAction.action());

            }catch(UnknownCommandException e){
                ui.renderErrorMessage(e);
            }

        }
        this.store.writeTaskList();
        this.ui.close();        
    }
    /**
     * Greets the user with a welcome message
     */
    private void greet(){
        ui.renderChatBotMessage("Hello I am your Chatbot "+ ChatBot.name 
        + " \n Anything in your mind right now? Want to walk by the sea?");

    }
    /**
     * Display the goodbye message
     */
    private void exit(){
        ui.renderChatBotMessage("See you.");
    }

    


}
