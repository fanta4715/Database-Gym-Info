package com.db.phase4.service;

import com.db.phase4.dao.UserDao;
import com.db.phase4.dto.trainer.TrainerRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public void registerTrainer(TrainerRegisterDto trainerRegisterDto) {
        userDao.registerTrainer(trainerRegisterDto);
    }

    public int findPtTrainerId(int userId) {
        return userDao.findPtTrainerId(userId);
    }
}
