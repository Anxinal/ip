package shiroha.command;

import shiroha.exceptions.UnknownCommandException;
import shiroha.tasks.TaskList;
import shiroha.tasks.Task;

abstract class AddTaskCommand extends Command{
            /**
             * Checks if the command is a valid add task command
             * @param line the user input
             * @return true if the command is a valid add task command, false otherwise
             * @throws UnknownCommandException if the command is not valid
             */
        public static boolean check(String line){

            if(!(line.startsWith("todo") || line.startsWith("deadline") || line.startsWith("event"))) {
                return false;
            }
            if(line.split(" ").length == 1) {
                throw new UnknownCommandException("What are you exactly going to do?");
            } 
            else {
                return true;
            }

        
            }

        public static AddTaskCommand convertTaskCommand(String line, TaskList taskList){

            if(TodoCommand.check(line)){ 
                return new TodoCommand(line.split("todo ")[1], taskList);
            }
            else if(EventCommand.check(line)){
                return new EventCommand(line.split("event ")[1], taskList);
            }
            else if(DeadlineCommand.check(line)){
                return new DeadlineCommand(line.split("deadline ")[1], taskList);
            }
            else{
                throw new UnknownCommandException("I don't understand what task you want to add");       
            }

        }

            public AddTaskCommand(String[] args, TaskList taskList){
                super(args, taskList);
            }
            
            @Override 
            public String toString(){
                return "New task comming :> \n";
            }

            private static class TodoCommand extends AddTaskCommand{
            /**
             * Checks if the command is a valid todo command
             */
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
             /**
             * Checks if the command is a valid command to create an event task
             */
            public static boolean check(String line){
                if(!line.startsWith("event")) return false;
                if(!line.contains(" /from ")) throw new UnknownCommandException("From What time?");
                if(!line.contains(" /to ")) throw new UnknownCommandException("Until When?");
                return true;
            }

            private EventCommand(String eventDetails,TaskList taskList){
                super(new String[3], taskList);

                String[] temp = eventDetails.split(" /from ");
                if(temp.length < 2 || temp[0].trim().equals("")) {
                    throw new UnknownCommandException("I will give a random name to your task list XD!");
                }

                this.args[0] = temp[0];
                String[] temp2 = temp[1].split(" /to ");

                if(temp2.length < 2 || temp2[0].trim().equals("")) {
                    throw new UnknownCommandException("A non-existent event that starts in another dimension!");
                }

                this.args[1] = temp2[0];
                this.args[2] = temp2[1];
                
            }

            @Override
            public String action(){
                return super.toString() + taskList.add(Task.TaskType.EVENT, this.args).toString();
            }

        }

        private static class DeadlineCommand extends AddTaskCommand{
            /**
             * Checks if the command is a valid command to create a deadline task
             */
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
                if(temp.length < 2 || temp[0].trim().equals("")) {
                    throw new UnknownCommandException("I will give a random name to your task list XD!");
                }

                this.args[0] = temp[0];
                this.args[1] = temp[1];
                
            }

            @Override
            public String action(){
                return super.toString() + taskList.add(Task.TaskType.DEADLINE, this.args).toString();
            }

        }

        }
