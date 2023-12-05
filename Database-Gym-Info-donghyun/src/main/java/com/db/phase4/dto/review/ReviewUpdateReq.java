package com.db.phase4.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewUpdateReq {
    private int reviewId;
    private int userId;
    private int gymId;
    private String comment;
    private int rating;
}
