package com.db.phase4.dto.gym;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
//db에서 gym 테이블의 튜블을 가져올 때 대응되는 클래스
public class GymAndUserCountViewDto {
    private int userNumber;
    private String gymName;
    private int gymId;
    private String gender;
}
