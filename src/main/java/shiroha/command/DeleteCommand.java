package shiroha.command;

import shiroha.exceptions.UnknownCommandException;
import shiroha.tasks.TaskList;

public class DeleteCommand extends Command {
            
    protected DeleteCommand(String line, TaskList taskList){
        super(new String[]{line.split(" ")[1]}, taskList);
    }     

    @Override
    public String action(){               
        try {
            String confirmation = taskList.delete(Integer.parseInt(args[0])).toString();
            return String.format("You don't want this task anymore? OK... done! : %n %s %n You still have %d tasks in your list.", 
                        confirmation, 
                        taskList.getSize());
        } catch (IndexOutOfBoundsException e) {
            throw new UnknownCommandException("This number points to an unknown empty message to delete. What should I do...");
        }
    }

    /**
    * Checks if the command is a valid command to delete a task
    */
    public static boolean check(String line){
        if(!line.startsWith("delete")) {
            return false;
        } else if(line.trim().equals("delete")) {
            throw new UnknownCommandException("I am going to delete your whole list if you don't tell me which one to!");
        } else {
            checkNumberFormatInCommand(line.split("delete "));
            return true;
        } 
    }
    /**
     * A helper function that checks whether a number has the correct format
     * @param splitedArgs The parts of the argument in array after being splited by the identifier
     * @throws UnknownCommandException when the number argument cannot be parsed to an integer or does not exist
     * 
     */
    private static void checkNumberFormatInCommand(String[] splitedArgs) {
        try {
            int index = Integer.parseInt(splitedArgs[1]);
            if (index < 0) {
                throw new UnknownCommandException("Your number does not look right...");
            }
        } catch (NumberFormatException e) {
            throw new UnknownCommandException("Is that a number from a different dimension?");
        } catch (IndexOutOfBoundsException e){
            throw new UnknownCommandException("I didn't see any number...");
        }
    }
}