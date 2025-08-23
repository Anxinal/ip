import java.lang.classfile.instruction.StoreInstruction;
import java.util.Scanner;


public class ChatBot {

    private static final String name = "SHIROHA"; 
    private static final String logo = "Chatbot - Shiroha XD";
    private Storage store;
    private UI ui;

    public ChatBot(){
        store = Storage.initialiseStorage("");
        ui = new UI();
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
                Command nextAction = Command.processAction(next, store.readTaskList());
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
