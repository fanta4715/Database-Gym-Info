package com.db.phase4.dto;


import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MachineDto {
    private int machineId;
    private String name;
    private String type;
    private String targetMuscle;
    private String state;
    private int gymId;

    public static List<MachineDto> of(ResultSet rs) throws SQLException {
        //make rs to list
        List<MachineDto> machineDtoList = new ArrayList<>();
        while (rs.next()) {
            MachineDto machineDto = new MachineDto();
            machineDto.setMachineId(rs.getInt("MACHINE_ID"));
            machineDto.setName(rs.getString("NAME"));
            machineDto.setType(rs.getString("TYPE"));
            machineDto.setTargetMuscle(rs.getString("TARGET_MUSCLE"));
            machineDto.setState(rs.getString("STATE"));
            machineDto.setGymId(rs.getInt("GYM_ID"));
            machineDtoList.add(machineDto);
        }
        return machineDtoList;
    }
}
