import java.lang.classfile.instruction.StoreInstruction;
import java.util.Scanner;


public class ChatBot {

    private static final String name = "SHIROHA"; 
    private static final String logo = "Chatbot - Shiroha XD";
    private Storage store;
    private UI ui;
    private Parser parser;

    public ChatBot(){
        store = Storage.initialiseStorage("");
        ui = new UI();
        parser = new Parser(store.readTaskList());
    }

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
        this.ui.close();        
    }

    private void greet(){
        ui.renderChatBotMessage("Hello I am your Chatbot "+ ChatBot.name 
        + " \n Anything in your mind right now? Want to walk by the sea?");

    }
    private  void exit(){
        ui.renderChatBotMessage("See you.");
    }

    


}
