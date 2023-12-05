package com.db.phase4.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ReviewContentDto {
    private int rating;
    private String comment;
}
