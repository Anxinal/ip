package shiroha.tasks;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import shiroha.exceptions.UnknownCommandException;


public class Task implements Serializable{
    
    private String description;
    private boolean isDone;
    private static final long serialVersionUID = 1000;

    public enum TaskType {
        TODO,
        EVENT,
        DEADLINE
    }
    /**
     * returns the string representation of the task, including its status and description
     */
    @Override
    public String toString(){
        String finishStat = isDone ? "X" : " ";
        return "["+ finishStat +"]" + " " + description;
    }
    private Task(String description){
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
            default -> throw new UnknownCommandException("");
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
    private static class TodoTask extends Task{
        private TodoTask(String description){
            super(description);
        }
        @Override
        public String toString(){
            return "[T]" + super.toString();
        }
    }
    private static class EventTask extends Task{
        LocalDate from;
        LocalDate to;
        static final String DATE_PRINT_FORMAT = "MMM dd yy";

        private EventTask(String description, String from, String to){
            super(description);
            try {
                this.from = LocalDate.parse(from);
                this.to = LocalDate.parse(to);
                if(this.from.isAfter(this.to)) throw new UnknownCommandException("Time travel? Back to the future?");
            } catch (DateTimeParseException e) {
                throw new UnknownCommandException("Your event date comes from... imagination?");
            }

        }
        /**
         * * Checks if the event is happening today
         * @return true if the event is happening today, false otherwise
         */
        private boolean isHappening(){
            LocalDate today = LocalDate.now();
            return (today.isEqual(from) || today.isAfter(from)) && (today.isEqual(to) || today.isBefore(to)) && !this.isDone();
        }
        @Override
        public String toString(){
             String happeningMarker = isHappening() ? " (Happening now!)" : "";

            return happeningMarker + "[E]" + super.toString() + String.format(" (from: %s to: %s)", 
                                                            from.format(DateTimeFormatter.ofPattern(DATE_PRINT_FORMAT)), 
                                                            to.format(DateTimeFormatter.ofPattern(DATE_PRINT_FORMAT)));
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            EventTask other = (EventTask) obj;
            return super.equals(other) && this.from.equals(other.from) && this.to.equals(other.to);
        }
    }
    private static class DeadlineTask extends Task{
        LocalDate deadline;
        static final String DATE_PRINT_FORMAT = "MMM dd yy";

        private DeadlineTask(String description, String deadline){
            super(description);
            try {
               this.deadline =LocalDate.parse(deadline); 
            } catch (DateTimeParseException e) {
                throw new UnknownCommandException("Your deadline date comes from... imagination? Or make it in yyyy-mm-dd format");
            }
            
        }
        /**
         * Checks if the event is already overdue based on the current date
         * @return true if the task is already overdue, false otherwise
         */
        private boolean isOverDue(){
            return this.deadline.isBefore(LocalDate.now()) && !this.isDone();
        }

        @Override
        public String toString(){
            String overDueMarker = isOverDue() ? " (Overdue!)" : "";
            return overDueMarker + "[D]" + super.toString() + String.format(" (by: %s)", deadline.format(DateTimeFormatter.ofPattern(DATE_PRINT_FORMAT)));
        }

        @Override
        public boolean equals(Object other) {
            if (this == other)  return true;
            if (other == null || getClass() != other.getClass()) return false;
            DeadlineTask otherDeadline = (DeadlineTask) other;
            return super.equals(otherDeadline) && this.deadline.equals(otherDeadline.deadline);
        }
    }


}
