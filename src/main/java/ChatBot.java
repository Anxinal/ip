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
                return new MarkCommand(line);
            }
            if(UnmarkCommand.check(line)){
                return new UnmarkCommand(line);
            }
            if(AddTaskCommand.check(line)){
                return AddTaskCommand.convertTaskCommand(line);
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
        private abstract static class AddTaskCommand extends Command{
            public static boolean check(String line){
                return line.startsWith("todo") || line.startsWith("deadline") || line.startsWith("event");
            }
            public static AddTaskCommand convertTaskCommand(String line){

                if(TodoCommand.check(line)){ 
                    return new TodoCommand(line.split("todo ")[1]);
                }

                if(EventCommand.check(line)){
                    return new EventCommand(line.split("event ")[1]);
                }

                return new DeadlineCommand(line.split("deadline ")[1]);
            }
            public AddTaskCommand(String[] args){
                super(args);
            }
            @Override 
            public String toString(){
                return "New task comming :> \n";
            }
        }

        private static class TodoCommand extends AddTaskCommand{
            
            public static boolean check(String line){
                return line.startsWith("todo");
            }

            private TodoCommand(String taskName){
                super(new String[]{taskName});
            }

            @Override
            public String action(){             
                return super.toString() + taskList.add(this.args[0]).toString();
            }

        }

        private static class EventCommand extends AddTaskCommand{
            
            public static boolean check(String line){
                return line.startsWith("event");
            }

            private EventCommand(String eventDetails){
                super(new String[3]);
                if(!eventDetails.contains(" /from ") || !eventDetails.contains(" /to ")){
                    throw new UnknownCommandException("The format for your new Event looks weird.");
                }
                String[] temp = eventDetails.split(" /from ");
                this.args[0] = temp[0];
                String[] temp2 = temp[1].split(" /to ");
                this.args[1] = temp2[0];
                this.args[2] = temp2[1];
                
            }

            @Override
            public String action(){
            
                return super.toString() + taskList.add(1, this.args).toString();
            }

        }

        private static class DeadlineCommand extends AddTaskCommand{
            
            public static boolean check(String line){
                return line.startsWith("deadline");
            }

            // No name should be included in event details
            private DeadlineCommand(String eventDetails){
                super(new String[2]);
                System.out.println(eventDetails);
                if(!eventDetails.contains(" /by ")){
                    throw new UnknownCommandException("Probably no the best way to create a new deadline task. :x"); 
                }
                String[] temp = eventDetails.split(" /by ");
                this.args[0] = temp[0];
                this.args[1] = temp[1];
                
            }

            @Override
            public String action(){
                return super.toString() + taskList.add(2, this.args).toString();
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
            private MarkCommand(String line){
                super(new String[]{line.split(" ")[1]});

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

            private UnmarkCommand(String line){
                super(new String[]{line.split(" ")[1]});
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
