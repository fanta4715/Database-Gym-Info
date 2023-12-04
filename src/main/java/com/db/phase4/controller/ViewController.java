package com.db.phase4.controller;

import com.db.phase4.dto.GymViewDto;
import com.db.phase4.dto.MachineViewDto;
import com.db.phase4.dto.RentalItemViewDto;
import com.db.phase4.dto.RentalViewDto;
import com.db.phase4.dto.review.ReviewViewDto;
import com.db.phase4.dto.trainer.TrainerViewDto;
import com.db.phase4.service.*;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final GymService gymService;
    private final RentalItemService rentalItemService;
    private final MachineService machineService;
    private final TrainerService trainerService;
    private final ReviewService reviewService;
    private final RentalService rentalService;

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping("/user/{userId}/gym") //구현완료
    public String gymSelectView(@PathVariable int userId, Model model) {
        List<GymViewDto> gyms = gymService.findAll(userId);
        model.addAttribute("gyms", gyms);
        return "gym";
    }

    @GetMapping("/user/{userId}/gym/{gymId}") //구현완료
    public String gymFunctionSelectView(@PathVariable int userId, @PathVariable int gymId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("gymId", gymId);
        return "gym-function";
    }

    @GetMapping("/user/{userId}/gym/{gymId}/rental-item")
    public String rentalItemView(@PathVariable int userId, @PathVariable int gymId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("gymId", gymId);
        model.addAttribute("rentals", rentalService.rentalSearchById(gymId, userId));
        return "rental-search";
    }

    @GetMapping("/user/{userId}/gym/{gymId}/machine")
    public String machineView(@PathVariable int userId, @PathVariable int gymId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("gymId", gymId);
        model.addAttribute("machines", machineService.machineSearchById(gymId));
        return "machine-search";
    }

    @GetMapping("/user/{userId}/gym/{gymId}/trainer")
    public String trainerView(@PathVariable int userId, @PathVariable int gymId, Model model) {
        List<TrainerViewDto> trainers = trainerService.findByGymId(gymId);
        model.addAttribute("userId", userId);
        model.addAttribute("gymId", gymId);
        model.addAttribute("trainers", trainers);
        return "trainer";
    }

    //
    @GetMapping("/user/{userId}/gym/{gymId}/review")
    public String reviewView(@PathVariable int userId, @PathVariable int gymId, Model model) {
        List<ReviewViewDto> reviews = reviewService.findByGymId(gymId);
        model.addAttribute("userId", userId);
        model.addAttribute("gymId", gymId);
        model.addAttribute("reviews", reviews);
        return "review";
    }
//
//    @GetMapping("/user/{userId}/detail")
//    public String userDetailView(@PathVariable int userId, Model model) {
////        UserViewDto user = userService.findById(userId);
////        model.addAttribute("user", user);
//        return "user-detail";
//    }

}
