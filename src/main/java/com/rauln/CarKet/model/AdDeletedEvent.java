package com.rauln.CarKet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class AdDeletedEvent extends Event{
   public AdDeletedEvent(String userEmail, String brand, String model){
       super(userEmail, brand, model);
   }
}
