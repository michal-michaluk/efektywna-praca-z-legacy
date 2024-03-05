package shortages.prediction;

import java.time.LocalDate;
import java.util.Map;

public class ProductionOutputs {
    private final Map<LocalDate, Long> outputs;

    public ProductionOutputs(Map<LocalDate, Long> collect) {
        this.outputs = collect;
    }

    public long get(LocalDate day) {
        return outputs.getOrDefault(day, 0L);
    }
}
