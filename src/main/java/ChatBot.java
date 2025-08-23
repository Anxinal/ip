import java.util.Scanner;


public class ChatBot {
    private static String name = "SHIROHA"; 
    private static String logo = "Chatbot - Shiroha XD";
    private static TaskList taskList = new TaskList();

   
    
    public static void start(){
        greet();
        Scanner s = new Scanner(System.in);
        while(true){
            try{
                String next = receiveInput(s);
                if(next.equals("bye")){
                    exit();
                    break;
                }
                Command nextAction = Command.processAction(next, taskList);
                System.out.println(nextAction.action());
                addLineBreak();
            }catch(UnknownCommandException e){
                System.out.println(e.getMessage());
                addLineBreak();
            }

        }
        s.close();
    }

    private static void greet(){
        System.out.println("Hello I am your Chatbot "+ ChatBot.name);
        System.out.println("Anything in your mind right now? Want to walk by the sea?");
        addLineBreak();
    }
    private static void exit(){
        System.out.println("See you.");
        addLineBreak();
    }
    private static void addLineBreak(){
        System.out.println("--------(-w-)---------");
    }
    
    private static String receiveInput(Scanner s){
        String next = s.nextLine();
        return next;
    }


}
