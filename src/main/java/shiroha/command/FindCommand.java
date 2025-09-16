package shiroha.command;

import shiroha.exceptions.UnknownCommandException;
import shiroha.tasks.TaskList;

public class FindCommand extends Command{

    protected FindCommand(String[] args, TaskList taskList){
        super(args, taskList);
    }

    @Override
    public String action(){
        String taskResult =  this.taskList.filter((task) -> task.getDescription().contains(this.args[0])).toString();
        return "I found all the following tasks that match your keyword:\n" + taskResult +"\nPraise me!";
    }
    /**
    * Checks if the command is a valid command to find tasks by keyword
    */
    public static boolean check(String line){
        if(!line.startsWith("find")) {
            return false;
        } else if(line.trim().equals("find")) {
            throw new UnknownCommandException("I should go and find a random task.");
        } else {
            return true;
        }       
    }
}
