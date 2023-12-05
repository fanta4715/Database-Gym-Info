package com.db.phase4.service;

import com.db.phase4.dao.RentalDao;
import com.db.phase4.dto.RentalDto;
import com.db.phase4.dto.RentalViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalDao rentalDao;

    public List<RentalViewDto> rentalSearchById(int gymId, int userId) {
        return rentalDao.getWtihGymId(gymId, userId);
    }

    //    "SELECT * FROM GYM G JOIN RENTAL_ITEM R ON G.Gym_id = R.Gym_id WHERE G.Name = 'gymName'"
    public String rentalSearchByName(String gymName) {
        return "hello";
    }

    public void requestRental(int userId, int gymId, String itemName) {
        rentalDao.requestRental(userId, gymId, itemName);
    }

    public void returnRental(int userId, int gymId, String itemName) {
        rentalDao.returnRental(userId, gymId, itemName);
    }
}
