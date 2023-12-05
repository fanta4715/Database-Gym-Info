package com.db.phase4.controller;

import com.db.phase4.dto.GymViewDto;
import com.db.phase4.dto.MachineViewDto;
import com.db.phase4.dto.UserViewDto;
import com.db.phase4.dto.review.ReviewCountDto;
import com.db.phase4.dto.review.ReviewViewDto;
import com.db.phase4.dto.trainer.FilteredTrainerViewDto;
import com.db.phase4.dto.trainer.TrainerViewDto;
import com.db.phase4.service.*;

import java.time.LocalDate;
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
    private final MachineService machineService;
    private final TrainerService trainerService;
    private final ReviewService reviewService;
    private final RentalService rentalService;
    private final UserService userService;

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
        model.addAttribute("machines", machineService.machineSearchById(gymId, userId));
        model.addAttribute("canUse", machineService.canUse(gymId, userId));
        model.addAttribute("canReserve", machineService.canReserve(gymId, userId));
        return "machine-search";
    }

    @GetMapping("/user/{userId}/gym/{gymId}/trainer")
    public String trainerView(@PathVariable int userId, @PathVariable int gymId, Model model) {
        List<TrainerViewDto> trainers = trainerService.findByGymId(gymId);
        model.addAttribute("ptTrainerId", userService.findPtTrainerId(userId));
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

    @GetMapping("/trainer-search")
    public String trainerSearchView() {
        return "trainer-search";
    }

    @GetMapping("/review-search")
    public String reviewSearchView() {
        return "review-search";
    }
    @GetMapping("/searched-trainer")
    public String searchedTrainerView(@RequestParam String specialization,
                                      @RequestParam int lowerYear,
                                      @RequestParam int upperYear,
                                      Model model) {
        List<FilteredTrainerViewDto> trainers = trainerService.findBySpecializationAndWorkYear(specialization, lowerYear, upperYear);
        model.addAttribute("trainers", trainers);
        return "trainer-list";
    }

    @GetMapping("/searched-review")
    public String searchedReviewView(@RequestParam String gender,
                                     @RequestParam LocalDate lowerBirthday,
                                     @RequestParam LocalDate upperBirthday,
                                     Model model) {

        List<ReviewCountDto> reviewers = reviewService.findByGenderAndAge(gender, lowerBirthday, upperBirthday);
        model.addAttribute("reviewers", reviewers);
        return "review-list";
    }

    @GetMapping("/user/{userId}/gym/{gymId}/people")
    public String peopleView(@PathVariable int userId, @PathVariable int gymId, Model model) {
        List<UserViewDto> people = userService.findByGymId(gymId);
        model.addAttribute("people", people);
        return "people";
    }

    @GetMapping("/searched-user")
    public String searchedUserView(@RequestParam int userId, Model model) {
        model.addAttribute("user", userService.findById(userId));
        return "searched-user";
    }
}
