package com.db.phase4.dto.gym;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
//db에서 gym 테이블의 튜블을 가져올 때 대응되는 클래스
public class UserDetailViewDto {
    private int userId;
    private String userName;
    private int usingMachineId;
    private int reserveMachineId;
    private int trainerId;
    private String birthDate;
    private String sex;
    private String contact;
    private int gymId;
    private String gymName;
}