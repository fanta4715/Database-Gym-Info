package com.db.phase4.dto.review;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewSaveReq {
    int userId;
    int gymId;
    String comment;
    int rating;
}
