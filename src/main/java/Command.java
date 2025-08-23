public abstract class Command {

        public abstract String action(); // return the command action with a return message to be displayed
        String[] args;
        TaskList taskList;
        private Command(String[] args, TaskList taskList){
            this.args = args;
            this.taskList = taskList;
        }

        public static Command processAction(String line, TaskList taskList){
            if(ListCommand.check(line)){
                return new ListCommand(taskList);
            }
            if(MarkCommand.check(line)){
                return new MarkCommand(line, taskList);
            }
            if(UnmarkCommand.check(line)){
                return new UnmarkCommand(line, taskList);
            }
            if(AddTaskCommand.check(line)){
                return AddTaskCommand.convertTaskCommand(line, taskList);
            }
            if(DeleteCommand.check(line)){
                return new DeleteCommand(line, taskList);
            }
            
            throw new UnknownCommandException("Stop talking like that! I want to understand you (;-;) ");
        }


        private abstract static class AddTaskCommand extends Command{

            public static boolean check(String line){

                if(!(line.startsWith("todo") || line.startsWith("deadline") || line.startsWith("event"))) return false;
                if(line.split(" ").length == 1) throw new UnknownCommandException("What are you exactly going to do?");
                return true;
            }

            public static AddTaskCommand convertTaskCommand(String line, TaskList taskList){

                if(TodoCommand.check(line)){ 
                    return new TodoCommand(line.split("todo ")[1], taskList);
                }

                if(EventCommand.check(line)){
                    return new EventCommand(line.split("event ")[1], taskList);
                }

                return new DeadlineCommand(line.split("deadline ")[1], taskList);
            }
            public AddTaskCommand(String[] args, TaskList taskList){
                super(args, taskList);
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

            private TodoCommand(String taskName, TaskList taskList){
                super(new String[]{taskName}, taskList);
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

            private EventCommand(String eventDetails,TaskList taskList){
                super(new String[3], taskList);

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
            private DeadlineCommand(String eventDetails, TaskList taskList){
                super(new String[2], taskList);
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
             private ListCommand(TaskList taskList){
                super(new String[0], taskList);
            }

            @Override
            public String action(){
                return this.taskList.toString();
            }
            public static boolean check(String line){
                return line.equals("list");
            }
        }
        private static class MarkCommand extends Command{
            static final String notifMessage = "That's happy news. Wait a moment..";
            private MarkCommand(String line, TaskList taskList){
                super(new String[]{line.split(" ")[1]}, taskList);

            }
          
            @Override
            public String action(){
                String message = this.taskList.switchTaskStatus(Integer.parseInt(this.args[0]), true);
                return notifMessage + "\n" + message;
            }

            public static boolean check(String line){
                if(!line.startsWith("mark")) return false;
                if(line.trim().equals("mark")) throw new UnknownCommandException("I am going to mark a random task for you to screw up your list!");
                try {
                   int index = Integer.parseInt(line.split("mark ")[1]);
                
                   if(index <= 0 ){
                    throw new UnknownCommandException("Your number does not look right...");
                   }

                } catch (NumberFormatException e) {
                    throw new UnknownCommandException("Is that even a number...");
                } catch (IndexOutOfBoundsException e){
                    throw new UnknownCommandException("Your number does not look right...");
                }
                return true;
            }
        }

        private static class UnmarkCommand extends Command{

            static final String notifMessage = "Never mind about that. I'll do it for you as well";

            private UnmarkCommand(String line, TaskList taskList){
                super(new String[]{line.split(" ")[1]}, taskList);
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

                } catch (NumberFormatException e) {
                    throw new UnknownCommandException("Is that even a number...");
                } catch (IndexOutOfBoundsException e){
                    throw new UnknownCommandException("Your number does not look right...");
                }
                return true;
            }
        }
        private static class DeleteCommand extends Command {
            private DeleteCommand(String line, TaskList taskList){
                super(new String[]{line.split(" ")[1]}, taskList);

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

                } catch (NumberFormatException e) {
                    throw new UnknownCommandException("Is that a number from a different dimension?");
                } catch (IndexOutOfBoundsException e){
                    throw new UnknownCommandException("Your number does not look right...");
                }
                return true;
            }            
        }
        
}
