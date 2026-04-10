package com.rauln.CarKet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdCreatedEvent {
    private final String userEmail;
    private final String carBrand;
    private final String carModel;
    private final int price;
}
