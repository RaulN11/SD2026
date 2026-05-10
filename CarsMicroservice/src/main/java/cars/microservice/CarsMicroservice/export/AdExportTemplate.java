package cars.microservice.CarsMicroservice.export;

import cars.microservice.CarsMicroservice.dtos.AdExportDTO;
import cars.microservice.CarsMicroservice.models.Advertisement;

import java.util.List;

public abstract class AdExportTemplate {
    public final String export(List<Advertisement> ads) throws Exception {
        List<AdExportDTO> dtos = transform(ads);
        return writeOutput(dtos);
    }

    protected List<AdExportDTO> transform(List<Advertisement> ads) {
        return ads.stream()
                .map(ad -> AdExportDTO.builder()
                        .id(ad.getId())
                        .brand(ad.getCar().getBrand())
                        .model(ad.getCar().getModel())
                        .chassis(ad.getCar().getChassis())
                        .year(ad.getYear())
                        .price(ad.getPrice())
                        .seller(ad.getFirstName() + " " + ad.getLastName())
                        .build())
                .toList();
    }

    protected abstract String writeOutput(List<AdExportDTO> dtos) throws Exception;

    public abstract String getContentType();

    public abstract String getFileExtension();
}