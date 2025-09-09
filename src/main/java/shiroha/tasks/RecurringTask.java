package shiroha.tasks;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Stream;

import shiroha.exceptions.UnknownCommandException;

public class RecurringTask extends Task {

    Stream<LocalDate> happenings;


    protected RecurringTask(String startDateString, int recurrence, String description) {
       
        super(description);
        try {
            LocalDate start = LocalDate.parse(startDateString);
            this.happenings = Stream.iterate(start, previousDay -> previousDay.plusDays(recurrence));
        } catch (DateTimeParseException e) {
            throw new UnknownCommandException("Your event date comes from... imagination?");
        }
    }

    private List<LocalDate> getNearestHappenings(int count) {

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
