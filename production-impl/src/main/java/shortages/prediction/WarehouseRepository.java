package shortages.prediction;

public interface WarehouseRepository {
    long get(String productRefNo);
}
