import java.util.Scanner;


public class ChatBot {
    private static String name = "SHIROHA"; 
    private static String logo = "Chatbot - Shiroha XD";
    private static TaskList taskList = new TaskList();
    private abstract static class Command {
        public abstract String action(); // return the command action with a return message to be displayed
        String[] args;
        private Command(String[] args){
            this.args = args;
        }

        public static Command processAction(String line){
            if(ListCommand.check(line)){
                return new ListCommand();
            }
            if(MarkCommand.check(line)){
                return new MarkCommand(line.split(" ")[1]);
            }
            if(UnmarkCommand.check(line)){
                return new UnmarkCommand(line.split(" ")[1]);
            }
            return new DefaultCommand(line);
        }

        private static class DefaultCommand extends Command{

            private DefaultCommand(String name){
                super(new String[]{name});
            }

            @Override
            public String action(){
                taskList.add(this.args[0]);
                return "added :" + this.args[0];
            }
        }


        private static class ListCommand extends Command{
             private ListCommand(){
                super(new String[0]);
            }

            @Override
            public String action(){
                return ChatBot.taskList.toString();
            }
            public static boolean check(String line){
                return line.equals("list");
            }
        }
        private static class MarkCommand extends Command{
            static final String notifMessage = "That's happy news. Wait a moment..";
            private MarkCommand(String number){
                super(new String[]{number});
            }
          
            @Override
            public String action(){
                String message = ChatBot.taskList.switchTaskStatus(Integer.parseInt(this.args[0]), true);
                return notifMessage + "\n" + message;
            }

            public static boolean check(String line){
                return line.startsWith("mark") && line.split(" ").length == 2;
            }
        }

        private static class UnmarkCommand extends Command{

            static final String notifMessage = "Never mind about that. I'll do it for you as well";

            private UnmarkCommand(String number){
                super(new String[]{number});
            }

            @Override
            public String action(){
                String message = ChatBot.taskList.switchTaskStatus(Integer.parseInt(this.args[0]), false);
                return notifMessage + "\n" + message;
            }
            public static boolean check(String line){
                return line.startsWith("unmark") && line.split(" ").length == 2;
            }
        }
        
    }
    
    public static void start(){
        greet();
        while(true){
            String next = receiveInput();
            if(next.equals("bye")){
                exit();
                break;
            }
            Command nextAction = Command.processAction(next);
            System.out.println(nextAction.action());
            addLineBreak();
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
        System.out.println("--------(-w-)---------");
    }
    
    private static String receiveInput(){
        Scanner s = new Scanner(System.in);
        String next = s.nextLine();
        return next;
    }


}
