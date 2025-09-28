package shiroha.command;

import shiroha.exceptions.UnknownCommandException;
import shiroha.tasks.TaskList;

public class MarkCommand extends Command{
    static final String notifMessage = "That's happy news. Wait a moment..";
   
    /**
     * Constructor for MarkCommand
     * @param line the user input
     * @param taskList the task list to be modified
     */
    protected MarkCommand(String line, TaskList taskList){
        super(new String[]{line.split(" ")[1]}, taskList);

    }
          
    @Override
    public String action(){

        try {
            String message = this.taskList.switchTaskStatus(Integer.parseInt(this.args[0]), true);
            return notifMessage + "\n" + message;
            } catch (IndexOutOfBoundsException e){
            throw new UnknownCommandException("Your number does not look right...");
            }
                     
        }
    /**
    * Checks if the command is a valid command to mark a task as done
    */
    public static boolean check(String line){

        if(!line.startsWith("mark")) {
            return false;
        }
        else if(line.trim().equals("mark")) {
            throw new UnknownCommandException("I am going to mark a random task for you to screw up your list!");
        }           
        else if(!Command.isConvertableToNumber(line.split("mark ")[1])) {
            throw new UnknownCommandException("Is that even a number...");
        }
        int markIndex = Integer.parseInt(line.split("mark ")[1]);
        if(markIndex <= 0){
            throw new UnknownCommandException("Your number does not look right...");
        }
        else{
                return true;
        }          
    }
}