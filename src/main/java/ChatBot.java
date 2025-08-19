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
            if(DeleteCommand.check(line)){
                return new DeleteCommand(line);
            }
            
            throw new UnknownCommandException("Stop talking like that! I want to understand you (;-;) ");
        }


        private abstract static class AddTaskCommand extends Command{

            public static boolean check(String line){

                if(!(line.startsWith("todo") || line.startsWith("deadline") || line.startsWith("event"))) return false;
                if(line.split(" ").length == 1) throw new UnknownCommandException("What are you exactly going to do?");
                return true;
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
                if(!line.startsWith("event")) return false;
                if(!line.contains(" /from ")) throw new UnknownCommandException("From What time?");
                if(!line.contains(" /to ")) throw new UnknownCommandException("Until When?");
                return true;
            }

            private EventCommand(String eventDetails){
                super(new String[3]);

                String[] temp = eventDetails.split(" /from ");
                if(temp.length < 2 || temp[0].trim().equals("")) throw new UnknownCommandException("I will give a random name to your task list XD!");
                this.args[0] = temp[0];
                String[] temp2 = temp[1].split(" /to ");
                 if(temp2.length < 2 || temp2[0].trim().equals("")) throw new UnknownCommandException("A non-existent event that starts in another dimension!");
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
                if(!line.startsWith("deadline")) return false;
                if(!line.contains(" /by ")) throw new UnknownCommandException("So what is the deadline?");
                return true;
            }

            // No name should be included in event details
            private DeadlineCommand(String eventDetails){
                super(new String[2]);
                if(!eventDetails.contains(" /by ") || eventDetails.endsWith(" /by ")){
                    throw new UnknownCommandException("Err What is the deadline for the this task ... :x "); 
                }
                String[] temp = eventDetails.split(" /by ");
                if(temp.length < 2 || temp[0].trim().equals("")) throw new UnknownCommandException("I will give a random name to your task list XD!");

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
                if(!line.startsWith("mark")) return false;
                if(line.trim().equals("mark")) throw new UnknownCommandException("I am going to mark a random task for you to screw up your list!");
                try {
                   int index = Integer.parseInt(line.split("mark ")[1]);
                
                   if(index <= 0 || index > taskList.getSize()){
                    throw new UnknownCommandException("Your number does not look right...");
                   }

                } catch (NumberFormatException e) {
                    throw new UnknownCommandException("Is that even a number...");
                }
                return true;
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
                if(!line.startsWith("unmark")) return false;
                if(line.trim().equals("unmark")) throw new UnknownCommandException("I am going to mark a random task for you to screw up your list!");
                try {
                   int index = Integer.parseInt(line.split("unmark ")[1]);
                
                   if(index <= 0 || index > taskList.getSize()){
                    throw new UnknownCommandException("Your number does not look right...");
                   }

                } catch (NumberFormatException e) {
                    throw new UnknownCommandException("Is that even a number...");
                }
                return true;
            }
        }
        private static class DeleteCommand extends Command {
            static final String notifMessage = "You don't want this task anymore? OK...";
            private DeleteCommand(String line){
                super(new String[]{line.split(" ")[1]});

            }     

            @Override
            public String action(){
                return String.format("You don't want this task anymore? OK... done! : %n %s %n You still have %d tasks in your list.", 
                                     taskList.delete(Integer.parseInt(args[0])).toString(), 
                                     taskList.getSize());
 
            }

            public static boolean check(String line){
                if(!line.startsWith("delete")) return false;
                if(line.trim().equals("delete")) throw new UnknownCommandException("I am going to delete your whole list if you don't tell me which one to!");
                try {
                   int index = Integer.parseInt(line.split("delete ")[1]);
                
                   if(index <= 0 || index > taskList.getSize()){
                    throw new UnknownCommandException("You shouldn't delete some that that does not exist...");
                   }

                } catch (NumberFormatException e) {
                    throw new UnknownCommandException("Is that a number from a different dimension?");
                }
                return true;
            }            
        }
        
    }
    
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
                Command nextAction = Command.processAction(next);
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
