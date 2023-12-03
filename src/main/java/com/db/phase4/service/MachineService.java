package com.db.phase4.service;

import com.db.phase4.dao.MachineDao;
import com.db.phase4.dto.MachineDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineService {
    private final MachineDao machineDao;

    public List<MachineDto> machineSearchById(String gymId) {
        return machineDao.getWithGymId(gymId);
    }

    public void requestMachine(String gymId, String machineId, String userId) {
        machineDao.requestMachine(gymId, machineId, userId);
    }

    public void reserveMachine(String gymId, String machineId, String userId) {
        machineDao.reserveMachine(gymId, machineId, userId);
    }
}
