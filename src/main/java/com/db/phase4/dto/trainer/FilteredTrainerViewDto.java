package com.db.phase4.dto.trainer;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilteredTrainerViewDto {
    String name;
    int trainerId;
    String specialization;
    int workYear;
    LocalDate birthDate;
    String sex;
    String contact;
    String gymName;
}
