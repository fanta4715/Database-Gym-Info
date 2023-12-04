package com.db.phase4.service;

import com.db.phase4.dao.UserDao;
import com.db.phase4.dao.MachineDao;
import com.db.phase4.dto.MachineViewDto;
import com.db.phase4.dto.gym.UserViewDto;
import com.db.phase4.dto.trainer.FilteredTrainerViewDto;
import com.db.phase4.dto.trainer.TrainerRegisterDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final MachineDao machineDao;

    public void registerTrainer(TrainerRegisterDto trainerRegisterDto) {
        userDao.registerTrainer(trainerRegisterDto);
    }

    public int findPtTrainerId(int userId) {
        return userDao.findPtTrainerId(userId);
    }

    public FilteredTrainerViewDto findTrainerById(int userId) {
        return userDao.findTrainerById(userId);
    }

    public List<UserViewDto> findByGymId(int gymId) {
        return userDao.findByGymId(gymId);
    }

    public UserViewDto findByUserId(int userId){ return userDao.findByUserId(userId);}

    public UserViewDto modifyUserInfo(String[] userInfoArray){return userDao.modifyUserInfo(userInfoArray);}

    public List<MachineViewDto> myMachineInfo(int userId) {
        return machineDao.myMachineInfo(userId);
    }
}
