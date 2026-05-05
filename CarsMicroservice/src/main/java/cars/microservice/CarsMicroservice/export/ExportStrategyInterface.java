package cars.microservice.CarsMicroservice.export;

import cars.microservice.CarsMicroservice.models.Advertisement;

import java.util.List;

public interface ExportStrategyInterface {
    String export(List<Advertisement> ads) throws Exception;
    String getContentType();
    String getFileExtension();
}
