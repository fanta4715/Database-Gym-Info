package com.db.phase4.service;

import com.db.phase4.dao.MachineDao;
import com.db.phase4.dao.UserDao;
import com.db.phase4.dto.HealthInfoDto;
import com.db.phase4.dto.RentalViewDto;
import com.db.phase4.dto.UserViewDto;
import com.db.phase4.dto.gym.UserDetailViewDto;
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


    public UserViewDto findById(int userId) {
        return userDao.findById(userId);
    }

    public List<HealthInfoDto> findHealthInfoById(int userId) {
        return userDao.findHealthInfoById(userId);
    }

    public boolean checkIfUserHasTrainer(int userId) {
        return userDao.checkIfUserHasTrainer(userId);
    }

    //-----------------추가 사항-------------------//
    public UserDetailViewDto findByUserId(int userId){ return userDao.findByUserId(userId);}

    public UserDetailViewDto modifyUserInfo(String[] userInfoArray){return userDao.modifyUserInfo(userInfoArray);}
}
