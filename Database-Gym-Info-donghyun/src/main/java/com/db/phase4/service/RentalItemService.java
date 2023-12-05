package com.db.phase4.service;

import com.db.phase4.dao.RentalItemDao;
import com.db.phase4.dto.RentalItemViewDto;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalItemService {
    private final RentalItemDao rentalItemDao;

    public List<RentalItemViewDto> findByGymId(int gymId) {
        return rentalItemDao.findByGymId(gymId);
    }
}
