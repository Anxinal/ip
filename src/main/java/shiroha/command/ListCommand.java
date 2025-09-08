package shiroha.command;

import shiroha.tasks.TaskList;

public class ListCommand extends Command{

    protected ListCommand(TaskList taskList){
        super(new String[0], taskList);
    }

    @Override
    public String action(){
        return this.taskList.toString();
    }
             /**
             * Checks if the command is a valid command to list all tasks
             */
    public static boolean check(String line){
        return line.equals("list");
    }
        }
