package shortages.prediction;

public class ShortageService {

    private final DemandsRepository demandRepository;
    private final ProductionOutputRepository productionRepository;
    private final WarehouseRepository warehouseRepository;

    public ShortageService(DemandsRepository demandRepository, ProductionOutputRepository productionRepository, WarehouseRepository warehouseRepository) {
        this.demandRepository = demandRepository;
        this.productionRepository = productionRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public Shortage predictShortages(String productRefNo, DataRange dates) {
        ProductionOutputs outputs = productionRepository.get(productRefNo, dates.firstDate());
        Demands demandsPerDay = demandRepository.get(productRefNo, dates.firstDate());
        long warehouseLevel = warehouseRepository.get(productRefNo);

        ShortagePrediction algorithm = new ShortagePrediction(productRefNo, dates, outputs, demandsPerDay, warehouseLevel);

        Shortage shortage = algorithm.predict();
        return shortage;
    }
}
