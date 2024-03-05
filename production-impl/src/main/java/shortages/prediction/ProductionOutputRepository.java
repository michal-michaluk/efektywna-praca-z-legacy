package shortages.prediction;

import dao.ProductionDao;
import entities.ProductionEntity;

import java.time.LocalDate;
import java.util.List;

public class ProductionOutputRepository {
    private final ProductionDao productionDao;

    public ProductionOutputRepository(ProductionDao productionDao) {
        this.productionDao = productionDao;
    }

    public ProductionOutputs get(String productRefNo, LocalDate today) {
        List<ProductionEntity> productions = productionDao.findFromTime(productRefNo, today.atStartOfDay());
        ProductionOutputs outputs = new ProductionOutputs(productions);
        return outputs;
    }
}
