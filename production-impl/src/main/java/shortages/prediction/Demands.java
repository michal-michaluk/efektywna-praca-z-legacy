package shortages.prediction;

import entities.DemandEntity;
import enums.DeliverySchema;
import tools.Util;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demands {
    private final Map<LocalDate, DemandEntity> demands;

    public Demands(List<DemandEntity> demands) {
        Map<LocalDate, DemandEntity> demandsPerDay = new HashMap<>();
        for (DemandEntity demand : demands) {
            demandsPerDay.put(demand.getDay(), demand);
        }
        this.demands = Collections.unmodifiableMap(demandsPerDay);
    }

    public DailyDemand get(LocalDate day) {
        if (demands.containsKey(day)) {
            return new DailyDemand(demands.get(day));
        } else {
            return null;
        }
    }

    public static class DailyDemand {
        private final DemandEntity demand;

        public DailyDemand(DemandEntity demand) {
            this.demand = demand;
        }

        public long calculateLevelOnDelivery(long level, long produced) {
            long levelOnDelivery;
            if (hasDeliverySchema(DeliverySchema.atDayStart)) {
                levelOnDelivery = level - getLevel();
            } else if (hasDeliverySchema(DeliverySchema.tillEndOfDay)) {
                levelOnDelivery = level - getLevel() + produced;
            } else if (hasDeliverySchema(DeliverySchema.every3hours)) {
                // TODO WTF ?? we need to rewrite that app :/
                throw new UnsupportedOperationException();
            } else {
                // TODO implement other variants
                throw new UnsupportedOperationException();
            }
            return levelOnDelivery;
        }

        public long calculateEndOfDayLevel(long level, long produced) {
            return level + produced - getLevel();
        }

        private DeliverySchema getDeliverySchema() {
            return Util.getDeliverySchema(demand);
        }

        private long getLevel() {
            return Util.getLevel(demand);
        }

        private boolean hasDeliverySchema(DeliverySchema schema) {
            return getDeliverySchema() == schema;
        }

    }
}
