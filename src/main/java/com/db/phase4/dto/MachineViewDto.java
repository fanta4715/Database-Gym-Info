package com.db.phase4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MachineViewDto {
    int machineId;
    String name;
    String type;
    String targetMuscle;
    String state;
}
