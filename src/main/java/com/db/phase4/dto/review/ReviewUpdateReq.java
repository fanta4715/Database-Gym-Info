package com.db.phase4.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewUpdateReq {
    int reviewId;
    int userId;
    int gymId;
    String comment;
    int rating;
}
