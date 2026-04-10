package com.rauln.CarKet.export;

import com.rauln.CarKet.dto.AdvertisementExportDTO;
import com.rauln.CarKet.model.Advertisement;
import org.springframework.stereotype.Component;

import java.util.List;
@Component("csvExport")
public class CsvExportStrategy implements ExportStrategyInterface{
    @Override
    public String export(List<Advertisement> ads) throws Exception {
        StringBuilder csv = new StringBuilder();
        List<AdvertisementExportDTO> toExport = ads.stream()
                .map(ad->AdvertisementExportDTO.builder()
                        .id(ad.getId())
                        .brand(ad.getCar().getBrand())
                        .model(ad.getCar().getModel())
                        .chassis(ad.getCar().getChassis())
                        .year(ad.getYear())
                        .price(ad.getPrice())
                        .seller(ad.getUser().getFirstName()+" "+ad.getUser().getLastName())
                        .build()
                ).toList();
        csv.append("ID, Brand, Model, Chassis, Year, Price\n");
        for(AdvertisementExportDTO ad: toExport){
            csv.append(ad.getId()).append(",")
                    .append(ad.getId()).append(",")
                    .append(ad.getBrand()).append(",")
                    .append(ad.getModel()).append(",")
                    .append(ad.getChassis()).append(",")
                    .append(ad.getYear()).append(",")
                    .append(ad.getPrice()).append(",")
                    .append(ad.getSeller()).append("\n");
        }
        return csv.toString();
    }
    @Override
    public String getContentType() {
        return "text/csv";
    }

    @Override
    public String getFileExtension() {
        return ".csv";
    }
}
