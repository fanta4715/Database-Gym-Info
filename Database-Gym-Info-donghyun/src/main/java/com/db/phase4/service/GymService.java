package com.db.phase4.service;

import com.db.phase4.dao.GymDao;

import java.util.List;

import com.db.phase4.dto.GymViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GymService {
    private final GymDao gymDao;

    public List<GymViewDto> findAll(int userId) {
        return gymDao.findAll(userId);
    }
}
