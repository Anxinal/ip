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


        

        
}
