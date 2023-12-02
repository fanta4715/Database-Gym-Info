package com.db.phase4.rental;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalDao rentalDao;

    public List<RentalDto> rentalSearchById(String gymId) {
        return rentalDao.getWtihGymId(gymId);
    }

//    "SELECT * FROM GYM G JOIN RENTAL_ITEM R ON G.Gym_id = R.Gym_id WHERE G.Name = 'gymName'"
    public String rentalSearchByName(String gymName) {
        return "hello";
    }
}
