package com.db.phase4.dto.gym;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
//db에서 gym 테이블의 튜블을 가져올 때 대응되는 클래스
public class TrainerViewDto {
    private String gymName;
    private int GymId;
    private int trainerId;
    private String trainerName;
    private String contact;
    private String specialization;
}
