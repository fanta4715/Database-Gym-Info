package com.db.phase4.service;

import com.db.phase4.dao.UserDao;
import com.db.phase4.dto.trainer.TrainerRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public void register(int userId, int trainerId) {
        userDao.register(new TrainerRegisterDto(userId, trainerId));
    }
}
