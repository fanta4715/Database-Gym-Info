package com.db.phase4.dto.gym;


import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
//db에서 gym 테이블의 튜블을 가져올 때 대응되는 클래스
public class GymViewDto {
    private int gymId;
    private String gymName;
    private String location;
    private String contact;
    private int capacity;
    private float avgRate;
    private int numOfMachine;
    private String someUserName;
}
