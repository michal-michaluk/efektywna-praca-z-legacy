package services.impl;

import entities.ShortageEntity;
import external.CurrentStock;
import shortages.prediction.*;

import java.time.LocalDate;
import java.util.List;

public class ShortageFinderACL {

    private static final boolean ENABLE_NEW_MODEL = true;

    private final DemandsRepository demandRepository;
    private final ProductionOutputRepository productionRepository;
    private final ShortageFinder legacy;

    public ShortageFinderACL(DemandsRepository demandRepository, ProductionOutputRepository productionRepository, ShortageFinder legacy) {
        this.demandRepository = demandRepository;
        this.productionRepository = productionRepository;
        this.legacy = legacy;
    }

    public List<ShortageEntity> findShortages(String productRefNo, LocalDate today, int daysAhead, CurrentStock stock) {
        if (ENABLE_NEW_MODEL) {
            ShortageService service = new ShortageService(
                    demandRepository,
                    productionRepository,
                    refNo -> stock.getLevel()
            );
            Shortage shortage = service.predictShortages(productRefNo, DataRange.startingFrom(today, daysAhead));
            return shortage.getEntities();
        } else {
            return legacy.findShortages(productRefNo, today, daysAhead, stock);
        }


    }

}
