package com.db.phase4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MachineViewDtoTwo {
    int classify;//1: 내가 사용중, 2: 내가 예약중
    int machineId;
    String name;
    String type;
    String targetMuscle;
    String state;

    public MachineViewDtoTwo(int i, int machineId, String name, String type, String targetMuscle, String state) {
        classify = i;
        machineId = machineId;
        name = name;
        type = type;
        targetMuscle = targetMuscle;
        state = state;
    }
}
