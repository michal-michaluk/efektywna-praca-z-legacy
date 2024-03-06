package acl;

import dao.DemandDao;
import entities.DemandEntity;
import shortages.prediction.Demands;
import shortages.prediction.DemandsRepository;
import tools.Util;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DemandsACLRepository implements DemandsRepository {
    private final DemandDao demandDao;

    public DemandsACLRepository(DemandDao demandDao) {
        this.demandDao = demandDao;
    }

    @Override
    public Demands get(String productRefNo, LocalDate today) {
        List<DemandEntity> demands = demandDao.findFrom(today.atStartOfDay(), productRefNo);

        Map<LocalDate, Demands.DailyDemand> mapped = demands.stream()
                .collect(Collectors.toUnmodifiableMap(
                        DemandEntity::getDay,
                        demand -> new Demands.DailyDemand(
                                Util.getLevel(demand),
                                Util.getDeliverySchema(demand)
                        ),
                        (a, b) -> b)
                );

        return new Demands(mapped);
    }
}
