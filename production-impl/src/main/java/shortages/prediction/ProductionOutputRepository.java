package shortages.prediction;

import java.time.LocalDate;

public interface ProductionOutputRepository {
    ProductionOutputs get(String productRefNo, LocalDate today);
}
