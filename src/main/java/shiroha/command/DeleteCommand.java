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
                if(!line.startsWith("delete")) return false;
                if(line.trim().equals("delete")) throw new UnknownCommandException("I am going to delete your whole list if you don't tell me which one to!");
                try {

                   int index = Integer.parseInt(line.split("delete ")[1]);

                } catch (NumberFormatException e) {
                    throw new UnknownCommandException("Is that a number from a different dimension?");
                } catch (IndexOutOfBoundsException e){
                    throw new UnknownCommandException("Your number does not look right...");
                }
                return true;
            }            
        }