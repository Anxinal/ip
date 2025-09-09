package shiroha.tasks;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import shiroha.exceptions.UnknownCommandException;


public class Task implements Serializable{
    
    private String description;
    private boolean isDone;
    private static final long serialVersionUID = 1000;
    static final String DATE_PRINT_FORMAT = "MMM dd yy";

    public enum TaskType {
        TODO,
        EVENT,
        DEADLINE,
        RECURRING
    }
    /**
     * returns the string representation of the task, including its status and description
     */
    @Override
    public String toString(){
        String finishStat = isDone ? "X" : " ";
        return "["+ finishStat +"]" + " " + description;
    }
    protected Task(String description){

        assert description.trim() != null;
        this.description = description;
        this.isDone = false;
    }
    /**
     * A factory method to create different types of tasks, where 0 = todo, 1 = event, 2 = deadline
     * @param type The type of task to create
     * @param details The details of the task to create
     * @return The created task
     */
    public static Task newTask(TaskType type, String[] details){
        
        switch(type){
            case TODO -> {
                return new TodoTask(details[0]);
            }
            case EVENT -> {
                return new EventTask(details[0], details[1], details[2]);
            }
            case DEADLINE -> {
                return new DeadlineTask(details[0], details[1]);
            }
            case RECURRING -> {
                return new RecurringTask(details[2], Integer.parseInt(details[1]), details[0]);
            }
            default -> {
                assert false: "Should not reach this line";
                throw new UnknownCommandException("");
            }
        }
    }
    
    /**
    * Returns true if the task is done, false otherwise
    */
    public boolean isDone(){
        return this.isDone;
    }
    /**
     * Marks the task as done, whether it is already done or not
     */
    public void mark(){
        this.isDone = true;
    }
        /**
     * Marks the task as not done,  whether it is already done or not
     */
    public void unmark(){
        this.isDone = false;
    }

    /**
     * Compares two tasks for equality based on their description and completion status
     * @param obj The object to compare with
     * @return true if the tasks are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task other = (Task) obj;
        return this.isDone == other.isDone && description.equals(other.description);
    }
    /**
     * Return the description of the task as how it is initialised
     * @return the string description of the task
     */
    public String getDescription(){
        return this.description;
    }




    


}
