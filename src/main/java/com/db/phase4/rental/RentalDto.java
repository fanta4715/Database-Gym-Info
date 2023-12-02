package com.db.phase4.rental;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RentalDto {
//    ITEM_NAME	VARCHAR2(15 BYTE)	No		1
//    GYM_ID	NUMBER	No		2
//    RENTAL_FEE	NUMBER	Yes		3
//    MAX_QUANTITY	NUMBER	Yes		4
    private String itemName;
    private String gymId;
    private int rentalFee;
    private int maxQuantity;

    // rental item's method
    //"of"
    public static List<RentalDto> of(ResultSet rs) throws SQLException {
        //make rs to list
        List<RentalDto> rentalDtoList = new ArrayList<>();
        while (rs.next()) {
            RentalDto rentalDto = new RentalDto();
            rentalDto.setItemName(rs.getString("ITEM_NAME"));
            rentalDto.setGymId(rs.getString("GYM_ID"));
            rentalDto.setRentalFee(rs.getInt("RENTAL_FEE"));
            rentalDto.setMaxQuantity(rs.getInt("MAX_QUANTITY"));
            rentalDtoList.add(rentalDto);
        }
        return rentalDtoList;
    }
}
