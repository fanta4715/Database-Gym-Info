package com.db.phase4.service;

import com.db.phase4.dao.GymDao;
import com.db.phase4.dto.gym.GymViewDto;
import com.db.phase4.dto.gym.TrainerViewDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GymSearchService {
    private final GymDao gymDao;

    //이거 대신에 @RequiredArgsConstructor 이거 써도 됨
    public GymSearchService(GymDao gymDao) {
        this.gymDao = gymDao;
    }

    public List<GymViewDto> findByNumOfPeople(String numOfPeople) {
        return gymDao.findByNumOfPeople(numOfPeople);
    }

    public List<TrainerViewDto> findByTrainerSpecialization(String[] specializations) {
        return gymDao.findByTrainerSpecialization(specializations);
    }

    public List<GymViewDto> findByReviewRate(String status) {
        return gymDao.findByReviewRate(status);
    }

    public List<GymViewDto> findByNumOfMachine(String status) {
        return  gymDao.findByNumOfMachine(status);
    }
}
