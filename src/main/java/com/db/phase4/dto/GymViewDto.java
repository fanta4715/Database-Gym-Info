package com.db.phase4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GymViewDto {
    int gymId;
    String name;
    String location;
    String contact;
    int capacity;
}
