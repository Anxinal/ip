package shiroha.tasks;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import shiroha.exceptions.UnknownCommandException;


public class DeadlineTask extends Task {

    private LocalDate deadline;


    protected DeadlineTask(String description, String deadline){
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
        return overDueMarker + "[D]" + super.toString() + String.format(" (by: %s)", deadline.format(DateTimeFormatter.ofPattern(Task.DATE_PRINT_FORMAT)));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) { 
            return true; 
        } else if (other == null || getClass() != other.getClass()) {
            return false;
        } else {
            DeadlineTask otherDeadline = (DeadlineTask) other;
            return super.equals(otherDeadline) && this.deadline.equals(otherDeadline.deadline);
        }
    }
}

