package com.rauln.CarKet.export;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.rauln.CarKet.dto.AdvertisementExportDTO;
import com.rauln.CarKet.model.Advertisement;
import org.springframework.stereotype.Component;

import java.util.List;
@Component("xmlExport")
public class XmlExportStrategy implements ExportStrategyInterface{
    private final XmlMapper xmlMapper = new XmlMapper();
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
