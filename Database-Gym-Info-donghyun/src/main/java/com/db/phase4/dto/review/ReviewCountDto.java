package com.db.phase4.dto.review;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class ReviewCountDto {
    String name;
    LocalDate birthdate;
    String sex;
    int reviewCount;
}
