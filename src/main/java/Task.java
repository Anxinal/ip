
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Task implements Serializable{
    
    private String description;
    private boolean isDone;
    private static final long serialVersionUID = 1000;

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
    public static Task newTask(int type, String[] details){
        switch(type){
            case 0 -> {
                return new TodoTask(details[0]);
            }
            case 1 -> {
                return new EventTask(details[0], details[1], details[2]);
            }
            case 2 -> {
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
            return overDueMarker + "[D]" + super.toString() + String.format(" (by %s)", deadline.format(DateTimeFormatter.ofPattern(DATE_PRINT_FORMAT)));
        }
    }


}
