package com.db.phase4.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RentalDto {
    private String itemName;
    private String gymId;
    private int rentalFee;
    private int maxQuantity;

    public static List<RentalDto> of(ResultSet rs) throws SQLException {
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

    public static List<RentalDto> setGymIdAndItemNameWithResultSet(ResultSet rs) throws SQLException {
        List<RentalDto> rentalDtoList = new ArrayList<>();
        while (rs.next()) {
            RentalDto rentalDto = new RentalDto();
            rentalDto.setItemName(rs.getString("ITEM_NAME"));
            rentalDto.setGymId(rs.getString("GYM_ID"));
            rentalDtoList.add(rentalDto);
        }
        return rentalDtoList;
    }
}
