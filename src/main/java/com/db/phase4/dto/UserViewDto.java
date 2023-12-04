package com.db.phase4.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserViewDto {
    String name;
    LocalDate birthdate;
    String sex;
}
