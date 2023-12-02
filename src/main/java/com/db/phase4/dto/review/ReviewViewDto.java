package com.db.phase4.dto.review;


import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewViewDto {
    private int reviewId;
    private String comment;
    private int rating;
    private LocalDate date;
    private String userName;
}
