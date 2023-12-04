package com.db.phase4.service;

import com.db.phase4.dao.MachineDao;
import com.db.phase4.dto.MachineDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import com.db.phase4.dto.MachineViewDto;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MachineService {
    private final MachineDao machineDao;

    public List<MachineViewDto> machineSearchById(int gymId, int userId) {
        return machineDao.getWithGymId(gymId, userId);
    }

    public void reserveMachine(String gymId, String machineId, String userId) {
        machineDao.reserveMachine(gymId, machineId, userId);
    }
}
