package shiroha.command;

import shiroha.exceptions.UnknownCommandException;
import shiroha.tasks.TaskList;

public abstract class Command {
       
        /**
         * Executes the command and returns the string output as the message to be displayed by the chatbot
         * @return The string output after executing the command
         */
        public abstract String action();
        String[] args;
        TaskList taskList;

        protected Command(String[] args, TaskList taskList){
            this.args = args;
            this.taskList = taskList;
        }

        /**
         * A helper function for all types of commands(including all subclasses) to check if a string is a number
         * @param s the string to be checked
         * @return whether the string can be converted to a number
         */
        protected static boolean isConvertableToNumber(String s){
            try {
                Integer.parseInt(s);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        /**
         * Processes the user input and returns the corresponding command
         * @param line the user input
         * @param taskList reference to current task list
         * @return the command to be executed
         * @throws UnknownCommandException if the command is not recognized
         */
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
            if(FindCommand.check(line)){
                return new FindCommand(new String[]{line.split("find ")[1]}, taskList);
            }
            
            throw new UnknownCommandException("Stop talking like that! I want to understand you (;-;) ");
        }      

        

        
}
