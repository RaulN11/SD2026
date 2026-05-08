package cars.microservice.CarsMicroservice.export;

import cars.microservice.CarsMicroservice.dtos.AdExportDTO;
import cars.microservice.CarsMicroservice.models.Advertisement;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("xmlExport")
public class XmlExportStrategy implements ExportStrategyInterface{
    private final XmlMapper xmlMapper = new XmlMapper();
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
        return xmlMapper.writeValueAsString(toExport);
    }

    @Override
    public String getContentType() {
        return "application/xml";
    }

    @Override
    public String getFileExtension() {
        return ".xml";
    }
}