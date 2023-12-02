package com.db.phase4.machine;

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
}
