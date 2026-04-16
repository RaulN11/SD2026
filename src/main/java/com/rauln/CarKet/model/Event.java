package com.rauln.CarKet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Event {
    private final String userEmail;
    private final String brand;
    private final String model;
}
