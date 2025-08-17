import java.util.Scanner;

public class ChatBot {
    private static String name = "SHIROHA"; 
    private static String logo = "Chatbot - Shiroha XD";
    private static TaskList taskList = new TaskList();
    private static final String[] KEYWORDS = {"list", "bye"};

    public static void start(){
        greet();
        while(true){
            String next = receiveInput();
            if(next.equals("bye")){
                exit();
                break;
            }
            if(next.equals("list")){
                printList();
                continue;
            }
            echo(next);
        }
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
        System.out.println("----------------");
    }
    
    private static String receiveInput(){
        Scanner s = new Scanner(System.in);
        String next = s.nextLine();
        if(!isCommandKeyWord(next)) taskList.add(next);
        return next;
    }
    private static void printList(){
        System.out.println(taskList.toString());
    }

    private static boolean isCommandKeyWord(String command){
        for(String word: KEYWORDS){
            if(command.startsWith(word)) return true;
        }
        return false;
    }
    private static void echo(String toEcho){
       addLineBreak();
       System.out.println("added: " + toEcho);
       addLineBreak();
    }
}
