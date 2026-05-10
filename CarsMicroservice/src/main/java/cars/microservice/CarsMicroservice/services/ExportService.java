package cars.microservice.CarsMicroservice.services;

import cars.microservice.CarsMicroservice.export.AdExportTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExportService {

    private final Map<String, AdExportTemplate> strategies;

    public AdExportTemplate getStrategy(String format) {
        AdExportTemplate strategy = strategies.get(format.toLowerCase() + "Export");
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported export format: " + format);
        }
        return strategy;
    }
}