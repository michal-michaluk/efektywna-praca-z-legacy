package shortages.prediction;

import java.time.LocalDate;

public interface DemandsRepository {
    Demands get(String productRefNo, LocalDate today);
}
