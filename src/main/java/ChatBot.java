public class ChatBot {
    static String name = "SHIROHA"; 
    static String logo = "Chatbot - Shiroha XD";

    public static void greet(){
        System.out.println("Hello I am your Chatbot "+ ChatBot.name);
        System.out.println("Anything in your mind right now? Want to walk by the sea?");
        addLineBreak();
    }
    public static void exit(){
        System.out.println("See you.");
        addLineBreak();
    }
    public static void addLineBreak(){
        System.out.println("----------------");
    }
}
