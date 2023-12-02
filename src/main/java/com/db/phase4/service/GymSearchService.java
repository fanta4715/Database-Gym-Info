package com.db.phase4.service;

import com.db.phase4.dao.GymDao;
import com.db.phase4.dto.gym.GymViewDto;
import lombok.RequiredArgsConstructor;
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
}
