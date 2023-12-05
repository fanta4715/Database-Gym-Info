package com.db.phase4.controller;


import com.db.phase4.dao.UserDao;
import com.db.phase4.dto.MachineViewDto;
import com.db.phase4.dto.MachineViewDtoTwo;
import com.db.phase4.dto.gym.UserViewDto;

import com.db.phase4.dto.HealthInfoDto;
import com.db.phase4.dto.RentalDto;
import com.db.phase4.dto.trainer.FilteredTrainerViewDto;
import com.db.phase4.service.RentalService;
import com.db.phase4.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MypageController {
    private final UserService userService;
    private final RentalService rentalService;

    @GetMapping("/user/{userId}/mypage")
    public String mypageView(@PathVariable int userId, Model model) {
        model.addAttribute("userId", userId);
        return "mypage/mypage-function";
    }

    @GetMapping("/user/{userId}/mypage/rental-item")
    public String myRentalItemView(@PathVariable int userId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("rentalItems", rentalService.findRentalItemById(userId));
        return "mypage/mypage-rental";
    }

    @GetMapping("/user/{userId}/mypage/machine")
    public String myMachineView(@PathVariable int userId, Model model) {
        List<MachineViewDtoTwo> machineViewDtoList = userService.myMachineInfo(userId);
        System.out.println(userId);
        model.addAttribute("machineList", machineViewDtoList);
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
        System.out.println("H");
        UserViewDto userViewDto = userService.findByUserId(userId);
        System.out.println("I");
        model.addAttribute("user", userViewDto);
        System.out.println("J");
        return "mypage/mypage-userInfo";
    }

    @PostMapping("/user/{userId}/mypage/user-info/modify")
    public String myUserInfoModifyView(
            @PathVariable int userId, // @PathVariable 추가
            @RequestParam String userName,
            @RequestParam String birthDate,
            @RequestParam String contact,
            Model model) {

        // 문자열 배열에 필요한 값들을 담습니다.
        String[] userInfoArray = {String.valueOf(userId), userName, birthDate, contact}; // userId를 String으로 변환
        System.out.println("birthDate: " + birthDate);
        // 모델에 추가합니다.
        UserViewDto userViewDto = userService.modifyUserInfo(userInfoArray);
//        model.addAttribute("userInfoArray", userInfoArray);

        return "redirect:/user/" + userId + "/mypage/user-info";
    }


    @GetMapping("/user/{userId}/mypage/health-info")
    public String myHealthInfoView(@PathVariable int userId, Model model) {
        model.addAttribute("healthInfos",userService.findHealthInfoById(userId));
        return "mypage/mypage-healthInfo";
    }

}
