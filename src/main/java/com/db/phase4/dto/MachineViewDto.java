package com.db.phase4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MachineViewDto {
    private MachineDto machineDto;
    private boolean isUsing; // true 누군가 기구를 사용 중인 상태.
    private boolean isDoing; // true 본인이 사용 중인 상태.
    private boolean isReserved; // true 누군가 기구를 예약한 상태.
    private boolean isReserving; // true 본인이 예약한 상태.
    private boolean canUse; // true 사용 가능한 상태.
    private boolean canReserve; // true 예약 가능한 상태.

    public MachineViewDto(MachineDto machineDto, boolean isUsing, boolean isDoing, boolean isReserved, boolean isReserving, boolean canUse, boolean canReserve) {
        this.machineDto = machineDto;
        this.isUsing = isUsing;
        this.isDoing = isDoing;
        this.isReserved = isReserved;
        this.isReserving = isReserving;
        this.canUse = canUse;
        this.canReserve = canReserve;
    }
}
