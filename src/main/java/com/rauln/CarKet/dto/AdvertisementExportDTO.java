package com.rauln.CarKet.dto;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class AdvertisementExportDTO {
    private Long id;
    private String brand;
    private String model;
    private String chassis;
    private Integer year;
    private Integer price;
    private String seller;
}
