package com.db.phase4.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalViewDto {
    private RentalDto rentalDto;
    private int currentQuantity;
    private boolean isRented;

    public RentalViewDto(RentalDto rentalDto, int currentQuantity, boolean isRented) {
        this.rentalDto = rentalDto;
        this.currentQuantity = currentQuantity;
        this.isRented = isRented;
    }
}
