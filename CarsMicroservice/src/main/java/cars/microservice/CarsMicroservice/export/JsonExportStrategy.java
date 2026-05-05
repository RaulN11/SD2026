package cars.microservice.CarsMicroservice.export;

import cars.microservice.CarsMicroservice.dtos.AdExportDTO;
import cars.microservice.CarsMicroservice.models.Advertisement;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
@Component("jsonExport")
public class JsonExportStrategy implements ExportStrategyInterface{
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String export(List<Advertisement> ads) throws Exception {
       List<AdExportDTO> toExport = ads.stream()
               .map(ad->AdExportDTO.builder()
                       .id(ad.getId())
                       .brand(ad.getCar().getBrand())
                       .model(ad.getCar().getModel())
                       .chassis(ad.getCar().getChassis())
                       .year(ad.getYear())
                       .price(ad.getPrice())
                       .seller(ad.getFirstName()+" "+ad.getLastName())
                       .build()
               ).toList();
       return objectMapper.writeValueAsString(toExport);
    }

    @Override
    public String getContentType() {
        return "application/json";
    }

    @Override
    public String getFileExtension() {
        return ".json";
    }
}
