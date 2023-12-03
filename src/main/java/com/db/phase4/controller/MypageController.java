package com.db.phase4.controller;

import com.db.phase4.dto.trainer.FilteredTrainerViewDto;
import com.db.phase4.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class MypageController {
    private final UserService userService;

    @GetMapping("/user/{userId}/mypage")
    public String mypageView(@PathVariable int userId, Model model) {
        model.addAttribute("userId", userId);
        return "mypage/mypage-function";
    }

    @GetMapping("/user/{userId}/mypage/rental-item")
    public String myRentalItemView(@PathVariable int userId, Model model) {
        return "mypage/mypage-rental";
    }

    @GetMapping("/user/{userId}/mypage/machine")
    public String myMachineView(@PathVariable int userId, Model model) {
        return "mypage/mypage-machine";
    }

    @GetMapping("/user/{userId}/mypage/trainer")
    public String myTrainerView(@PathVariable int userId, Model model) {
        FilteredTrainerViewDto myTrainer = userService.findTrainerById(userId);
        model.addAttribute("myTrainer", myTrainer);
        return "mypage/mypage-trainer";
    }

    @GetMapping("/user/{userId}/mypage/user-info")
    public String myUserInfoView(@PathVariable int userId, Model model) {

        return "mypage/mypage-userInfo";
    }

    @GetMapping("/user/{userId}/mypage/health-info")
    public String myHealthInfoView(@PathVariable int userId, Model model) {
        return "mypage/mypage-healthInfo";
    }

}
