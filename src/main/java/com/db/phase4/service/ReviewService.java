package com.db.phase4.service;

import com.db.phase4.dao.ReviewDao;
import com.db.phase4.dto.review.ReviewContentDto;
import com.db.phase4.dto.review.ReviewSaveReq;
import com.db.phase4.dto.review.ReviewUpdateReq;
import com.db.phase4.dto.review.ReviewViewDto;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewDao reviewDao;

    public void delete(int reviewId) {
        reviewDao.deleteReview(reviewId);
    }

    public List<ReviewViewDto> findByGymId(int gymId) {
        return reviewDao.findByGymId(gymId);
    }

    public void save(ReviewSaveReq reviewReq) {
        reviewDao.save(reviewReq);
    }

    public void update(ReviewUpdateReq reviewReq) {
        reviewDao.update(reviewReq);
    }

    public ReviewContentDto findById(int reviewId) {
        return reviewDao.findById(reviewId);
    }
}
