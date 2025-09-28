package shiroha.command;

import shiroha.exceptions.UnknownCommandException;
import shiroha.tasks.TaskList;

public class UnmarkCommand extends Command{
            
    static final String NOTIF_MESSAGE = "Never mind about that. I'll do it for you as well";

    /**
     * Constructor for UnmarkCommand
     * @param line the user input
     * @param taskList the task list to be modified
     */
    protected UnmarkCommand(String line, TaskList taskList){
        super(new String[]{line.split(" ")[1]}, taskList);
    }

    @Override
    public String action(){
        try{
            String message = this.taskList.switchTaskStatus(Integer.parseInt(this.args[0]), false);
            return NOTIF_MESSAGE + "\n" + message;

        } catch (IndexOutOfBoundsException e){
            throw new UnknownCommandException("Your number does not look right...");
        }

            }
             /**
             * Checks if the command is a valid command to mark a task as undone
             */
    public static boolean check(String line){

        if(!line.startsWith("unmark")) {
            return false;
        } 
        else if(line.trim().equals("unmark")) {
            throw new UnknownCommandException("I am going to mark a random task for you to screw up your list!");
        }
        else if(!isConvertableToNumber(line.split("unmark ")[1])){
            throw new UnknownCommandException("Is that even a number...");    
        }
        int markIndex = Integer.parseInt(line.split("unmark ")[1]);
        if(markIndex <= 0){
            throw new UnknownCommandException("Your number does not look right...");
         }            
        else{
            return true;
        }
                              
    }
        }
