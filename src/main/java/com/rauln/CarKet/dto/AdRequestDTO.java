package com.rauln.CarKet.dto;

import lombok.Data;

@Data
public class AdRequestDTO {
    private String brand;
    private String model;
    private String chassis;
    private Integer year;
    private Integer price;
}
