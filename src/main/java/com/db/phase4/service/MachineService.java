package com.db.phase4.service;

import com.db.phase4.dao.MachineDao;
import com.db.phase4.dto.MachineViewDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MachineService {
    private final MachineDao machineDao;

    public List<MachineViewDto> findByGymId(int gymId) {
        return machineDao.findByGymId(gymId);
    }
}
