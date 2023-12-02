package com.db.phase4.controller;

import com.db.phase4.dto.gym.GymViewDto;
import com.db.phase4.dto.gym.PersonViewDto;
import com.db.phase4.dto.gym.TrainerViewDto;
import com.db.phase4.dto.review.ReviewViewDto;
import com.db.phase4.service.GymSearchService;
import com.db.phase4.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GymSearchController {
    private final GymSearchService gymSearchService;

    @GetMapping("/gym/search/home")
    public String gymSearchPageMainHome(Model model){
        return "gym_search/gym_search";
    }
    //회원의 수가 특정 인원 수 미만인 gym 정보 조회
    @GetMapping("/gym/search/numOfPeople")
    public String numOfPeopleResultView(Model model) {
        return "gym_search/numOfPeopleForm";
    }

    @PostMapping("/gym/search/numOfPeople")
    public String numResultView(@RequestParam String numOfPeople, Model model) {
        List<GymViewDto> gymList = gymSearchService.findByNumOfPeople(numOfPeople);
        model.addAttribute("gymList", gymList);
        return "gym_search/numOfPeople";
    }

    //특정 전문분야의 트레이너의 정보와 해당 트레이너가 속한 gym 이름
    @PostMapping("/gym/search/trainer/specialization")
    public String trainerResultView(@RequestParam String specialization1, @RequestParam(required = false, defaultValue = "") String specialization2, Model model) throws Exception {
        String[] specializations = new String[]{specialization1, specialization2};
        List<TrainerViewDto> trainerList = gymSearchService.findByTrainerSpecialization(specializations);
        model.addAttribute("trainerList", trainerList);
        return "gym_search/trainerField";
    }

    @GetMapping("/gym/search/trainer/specialization")
    public String trainerFormView(Model model) {
        return "gym_search/trainerFieldForm";
    }

    @GetMapping("/gym/search/review/{status}")
    public String reviewResultView(@PathVariable String status, Model model) {
        List<GymViewDto> gymList = gymSearchService.findByReviewRate(status);
        model.addAttribute("gymList", gymList);
        return "gym_search/gymRate";
    }

    @GetMapping("/gym/search/numOfMachine/{status}")
    public String machineResultView(@PathVariable String status, Model model) {
        List<GymViewDto> gymList = gymSearchService.findByNumOfMachine(status);
        model.addAttribute("gymList", gymList);

        return "gym_search/numOfMachine";
    }

    @GetMapping("/gym/search/gymAndUser/{status}")
    public String gymAndUserOrderResultView(@PathVariable String status, Model model) {
        List<GymViewDto> gymList = gymSearchService.findByNameOfGymAndUser(status);
        model.addAttribute("gymList", gymList);

        return "gym_search/gymAndUser";
    }

    @GetMapping("/gym/search/trainerAndUserDistinct")
    public String trainerAndUserDistinctResultView(Model model) {
        List<PersonViewDto> personList = gymSearchService.findByPersonName();
        model.addAttribute("personList", personList);

        return "gym_search/person";
    }

}
