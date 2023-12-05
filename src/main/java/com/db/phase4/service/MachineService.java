package com.db.phase4.service;

import com.db.phase4.dao.MachineDao;
import com.db.phase4.dto.MachineDto;
import com.db.phase4.dto.gym.MachineDetailViewDto;
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

    public void reserveMachine(int gymId, int machineId, int userId, String reservation) {
        machineDao.reserveMachine(gymId, machineId, userId, reservation);
    }

    public boolean canUse(int gymId, int userId) {
        return machineDao.canUse(gymId, userId);
    }

    public boolean canReserve(int gymId, int userId) {
        return machineDao.canReserve(gymId, userId);
    }

    public List<MachineDetailViewDto> myMachineInfo(int userId){ return machineDao.myMachineInfo(userId);}
}
