import java.util.Scanner;

public class ChatBot {
    static String name = "SHIROHA"; 
    static String logo = "Chatbot - Shiroha XD";

    public static void start(){
        greet();
        while(true){
            String next = receiveInput();
            if(next.equals("bye")){
                exit();
                break;
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
        return s.nextLine();
    }

    private static void echo(String toEcho){
       addLineBreak();
       System.out.println(toEcho);
       addLineBreak();
    }
}
