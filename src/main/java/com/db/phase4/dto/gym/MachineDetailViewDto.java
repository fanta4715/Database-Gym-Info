package com.db.phase4.dto.gym;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class MachineDetailViewDto {
    int classify;//1: 내가 사용중, 2: 내가 예약중
    int machineId;
    String name;
    String type;
    String targetMuscle;
    String state;

    public MachineDetailViewDto(int id, int machineId, String name, String type, String targetMuscle, String state) {
        this.classify = id;
        this.machineId = machineId;
        this.name = name;
        this.type = type;
        this.targetMuscle = targetMuscle;
        this.state = state;
    }
}