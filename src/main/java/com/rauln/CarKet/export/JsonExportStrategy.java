package com.rauln.CarKet.export;

import com.rauln.CarKet.dto.AdvertisementExportDTO;
import com.rauln.CarKet.model.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Component("jsonExport")
public class JsonExportStrategy implements ExportStrategyInterface{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String export(List<Advertisement> ads) throws Exception {
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
