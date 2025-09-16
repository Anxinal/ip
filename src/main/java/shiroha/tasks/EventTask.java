package shiroha.tasks;

import java.awt.Taskbar;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import shiroha.exceptions.UnknownCommandException;

public class EventTask extends Task {
        
    private LocalDate from;
    private LocalDate to;

    protected EventTask(String description, String from, String to){

        super(description);
        assert description.trim() != null;
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
                                                                   from.format(DateTimeFormatter.ofPattern(Task.DATE_PRINT_FORMAT)), 
                                                                   to.format(DateTimeFormatter.ofPattern(Task.DATE_PRINT_FORMAT)));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EventTask other = (EventTask) obj;
        return super.equals(other) && this.from.equals(other.from) && this.to.equals(other.to);
    }

    }
