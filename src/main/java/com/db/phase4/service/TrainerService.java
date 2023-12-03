package com.db.phase4.service;

import com.db.phase4.dao.TrainerDao;
import com.db.phase4.dto.trainer.FilteredTrainerViewDto;
import com.db.phase4.dto.trainer.TrainerViewDto;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerDao trainerDao;


    public List<TrainerViewDto> findByGymId(int gymId) {
        return trainerDao.findByGymId(gymId);
    }

    public List<FilteredTrainerViewDto> findBySpecializationAndWorkYear(String specialization, int lowerYear, int upperYear) {
        return trainerDao.findBySpecializationAndWorkYear(specialization, lowerYear, upperYear);
    }
}
