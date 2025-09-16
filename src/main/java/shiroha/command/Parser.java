package shiroha.command;

import shiroha.tasks.TaskList;
import shiroha.exceptions.UnknownCommandException;

public class Parser {

    TaskList taskList;
    public Parser(TaskList taskList){
        this.taskList = taskList;
    }
    
    /**
     * Processes the user input and returns the corresponding command
     * @param line the user input
     * @return the command to be executed
     * @throws UnknownCommandException if the command is not recognized
     */
    public Command parse(String line){
        if(ListCommand.check(line)){
            return new ListCommand(taskList);
        } else if(MarkCommand.check(line)){
            return new MarkCommand(line, taskList);
        } else if(UnmarkCommand.check(line)){
            return new UnmarkCommand(line, taskList);
        } else if(AddTaskCommand.check(line)){
            return AddTaskCommand.convertTaskCommand(line, taskList);
        } else if(DeleteCommand.check(line)){
            return new DeleteCommand(line, taskList);
        } else if(FindCommand.check(line)){
            return new FindCommand(new String[]{line.split("find ")[1]}, taskList);
        } else {
            throw new UnknownCommandException("Stop talking like that! I want to understand you (;-;) ");
        }
    }


}
