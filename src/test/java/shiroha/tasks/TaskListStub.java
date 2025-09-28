package shiroha.tasks;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import shiroha.exceptions.UnknownCommandException;
public class TaskListStub {
    
    private String tasksString;
    int index = 1;

    public TaskListStub() {
        this.tasksString = "";
    }

    public void add(String description) {
        tasksString += index + ". [T][ ] " + description + "\n";
        index ++;
    }
    public void add(int type, String[] details) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yy");
        switch(type){
            case 1 -> {
                tasksString += this.index + ". [E][ ] " + details[0] + " (from: ";
                tasksString += LocalDate.parse(details[1]).format(formatter) + " to: " + LocalDate.parse(details[2]).format(formatter) + ")\n";
                index++;
            }
            case 2 -> {
                tasksString += this.index + ". [D][ ] " + details[0] + " (by: " + LocalDate.parse(details[1]).format(formatter) + ")\n";
                index++;
            }
            default -> throw new UnknownCommandException("");
        }
    }

    @Override
    public String toString() {
        return tasksString;
    }
    
     
}
