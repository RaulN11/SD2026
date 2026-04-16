package com.rauln.CarKet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class AdCreatedEvent extends Event{
    private final int price;
    public AdCreatedEvent(String userEmail, String brand, String model, int price){
        super(userEmail,brand,model);
        this.price=price;
    }
}
