package shiroha;

import shiroha.tasks.TaskList;

public class Parser {

    TaskList taskList;
    public Parser(TaskList taskList){
        this.taskList = taskList;
    }

    public Command parse(String line){
        return Command.processAction(line, this.taskList);
    }
}
