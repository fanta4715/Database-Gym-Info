package com.db.phase4.controller;

import com.db.phase4.dto.review.ReviewSaveReq;
import com.db.phase4.dto.review.ReviewUpdateReq;
import com.db.phase4.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/user/{userId}/gym/{gymId}/review-save")
    public String saveView(@PathVariable int gymId, @PathVariable int userId, Model model) {
        model.addAttribute("userId",userId);
        model.addAttribute("gymId", gymId);
        return "review-save";
    }

    @PostMapping("/review-save")
    public String saveReview(@ModelAttribute ReviewSaveReq reviewSaveReq) {
        reviewService.save(reviewSaveReq);
        //모델에 붙이는 로직을 실행하지 않았음. 저장하는 로직과 화면을 띄우는 로직을 분리하기 위해 아웃소싱하는 게
        //redirect의 목적인듯.
        return "redirect:/user/"+reviewSaveReq.getUserId()+"/gym/"+reviewSaveReq.getGymId()+"/review";
    }

//    @PostMapping("/review-delete")
//    public String deleteReview(@RequestParam("reviewId") int reviewId) {
//        reviewService.delete(reviewId);
//        return "redirect:/"++"/review";
//    }

    @GetMapping("/user/{userId}/gym/{gymId}/review-update")
    public String updateView(@PathVariable int gymId, @PathVariable int reviewId, Model model) {
        model.addAttribute("reviewId", reviewId);
        model.addAttribute("gymId", gymId);
        return "review-update";
    }

    @PostMapping("/review-update")
    public String updateReview(@PathVariable int gymId, @ModelAttribute ReviewUpdateReq reviewReq) {
        reviewService.update(reviewReq);
        return "redirect:/{"+gymId+"}/review";
    }
}
