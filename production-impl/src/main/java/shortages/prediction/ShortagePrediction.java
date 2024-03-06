package shortages.prediction;

import java.time.LocalDate;

public class ShortagePrediction {
    private final String productRefNo;
    private final DataRange dates;
    private final ProductionOutputs outputs;
    private final Demands demandsPerDay;
    private final long warehouseStock;

    public ShortagePrediction(String productRefNo, DataRange dates, ProductionOutputs outputs, Demands demandsPerDay, long warehouseStock) {
        this.productRefNo = productRefNo;
        this.dates = dates;
        this.outputs = outputs;
        this.demandsPerDay = demandsPerDay;
        this.warehouseStock = warehouseStock;
    }

    public Shortage predict() {
        Shortage.Builder shortage = Shortage.builder(productRefNo);
        long level = warehouseStock;
        for (LocalDate day : dates) {
            Demands.DailyDemand demand = demandsPerDay.get(day);
            long produced = outputs.get(day);

            long levelOnDelivery = demand.calculateLevelOnDelivery(level, produced);
            if (levelOnDelivery < 0) {
                shortage.add(day, levelOnDelivery);
            }
            long endOfDayLevel = demand.calculateEndOfDayLevel(level, produced);
            level = Math.max(endOfDayLevel, 0);
        }
        return shortage.build();
    }
}
