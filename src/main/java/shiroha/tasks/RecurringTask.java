package shiroha.tasks;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Stream;

import shiroha.exceptions.UnknownCommandException;

public class RecurringTask extends Task {


    private LocalDate start;
    private int interval;


    protected RecurringTask(String startDateString, int interval, String description) {
       
        super(description);
        try {
            LocalDate start = LocalDate.parse(startDateString);
            this.start = start;
            this.interval = interval;
        } catch (DateTimeParseException e) {
            throw new UnknownCommandException("Your event date comes from... imagination?");
        }
    }
    
    private List<LocalDate> getNearestHappenings(int count) {
        Stream<LocalDate> happenings = Stream.iterate(start, previousDay -> previousDay.plusDays(interval));
        LocalDate yesterday = LocalDate.now().minusDays(1); // so that today is also included
        return happenings.filter(current -> current.isAfter(yesterday))
                         .limit(count)
                         .toList();
    }

    private LocalDate getFirstHappening() {
        return getNearestHappenings(1).get(0);
      
    }

    @Override
    public String toString(){

        return String.format("[R] %s (Next happening on: %s)", 
                            super.toString(),
                            getFirstHappening().format(DateTimeFormatter.ofPattern(Task.DATE_PRINT_FORMAT)));
    }


}
