package shortages.prediction;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class DataRange implements Iterable<LocalDate> {

    private final List<LocalDate> dates;

    private DataRange(List<LocalDate> dates) {
        this.dates = dates;
    }

    public static DataRange startingFrom(LocalDate start, int daysAhead) {
        return new DataRange(
                Stream.iterate(start, date -> date.plusDays(1))
                        .limit(daysAhead)
                        .toList()
        );
    }

    @Override
    public Iterator<LocalDate> iterator() {
        return dates.iterator();
    }

    public LocalDate firstDate() {
        return dates.getFirst();
    }
}
