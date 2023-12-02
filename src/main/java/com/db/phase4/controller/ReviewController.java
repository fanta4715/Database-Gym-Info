package com.db.phase4.controller;

import com.db.phase4.dto.review.ReviewSaveReq;
import com.db.phase4.dto.review.ReviewUpdateReq;
import com.db.phase4.dto.review.ReviewViewDto;
import com.db.phase4.service.ReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{gymId}/review")
    public String indexView(@PathVariable String gymId, Model model) {
        List<ReviewViewDto> reviews = reviewService.findByGymId(gymId);
        model.addAttribute("reviews", reviews);
        return "review";
    }

    @GetMapping("/{gymId}/review-save")
    public String saveView(@PathVariable String gymId, Model model) {
        model.addAttribute("gymId", gymId);
        log.info("gymId: {}", gymId);
        return "review-save";
    }

    @PostMapping("/{gymId}/review")
    public String saveReview(@PathVariable String gymId, @ModelAttribute ReviewSaveReq reviewSaveReq) {
        reviewService.save(reviewSaveReq);
        //모델에 붙이는 로직을 실행하지 않았음. 저장하는 로직과 화면을 띄우는 로직을 분리하기 위해 아웃소싱하는 게
        //redirect의 목적인듯.
        return "redirect:/{"+gymId+"}/review";
    }

    @DeleteMapping("/{gymId}/review/{reviewId}")
    public String deleteReview(@PathVariable String gymId, @PathVariable String reviewId) {
        reviewService.delete(reviewId);
        return "redirect:/{"+gymId+"}/review";
    }

    @GetMapping("/{gymId}/review-update/{reviewId}")
    public String updateView(@PathVariable String gymId, @PathVariable String reviewId, Model model) {
        model.addAttribute("reviewId", reviewId);
        model.addAttribute("gymId", gymId);
        return "review-update";
    }

    @PutMapping("/{gymId}/review")
    public String updateReview(@PathVariable int gymId, @ModelAttribute ReviewUpdateReq reviewReq) {
        reviewService.update(reviewReq);
        return "redirect:/{"+gymId+"}/review";
    }
}
