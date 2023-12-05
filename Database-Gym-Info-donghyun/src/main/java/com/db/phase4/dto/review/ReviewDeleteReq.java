package com.db.phase4.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewDeleteReq {
    private int reviewId;
    private int userId;
    private int gymId;
}
