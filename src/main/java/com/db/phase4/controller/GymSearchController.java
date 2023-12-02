package com.db.phase4.controller;

import com.db.phase4.dto.gym.GymViewDto;
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

    //회원의 수가 특정 인원 수 미만인 gym 정보 조회
    @GetMapping("/gym/search/numOfPeople")
    public String numdResultView(Model model) {
        return "gym_search/numOfPeopleForm";
    }
    @PostMapping("/gym/search/numOfPeople")
    public String numResultView(@RequestParam String numOfPeople, Model model) {
        System.out.println("GymSearchController findByNum A");
        List<GymViewDto> gymList = gymSearchService.findByNumOfPeople(numOfPeople);
        System.out.println("GymSearchController findByNum B, gymList.size(): " + gymList.size());
        model.addAttribute("gymList", gymList);
        System.out.println("GymSearchController findByNum C");
        return "gym_search/numOfPeople";
    }

    //특정 전문분야의 트레이너의 정보와 해당 트레이너가 속한 gym 이름
    @PostMapping("/gym/search/trainer/specialization")
    public String trainerResultView(@RequestParam String specialization1, @RequestParam(required = false, defaultValue = "") String specialization2, Model model) throws Exception {
        System.out.println("GymSearchController findByTrainer Specialization A");
        System.out.println("specialization1: " + specialization1 + " specialization2: " + specialization2);
        String[] specializations = new String[]{specialization1, specialization2};
        System.out.println("GymSearchController: String[] [0]: " + specializations[0] + " [1]: " + specializations[1]);
        List<TrainerViewDto> trainerList = gymSearchService.findByTrainerSpecialization(specializations);
        System.out.println("GymSearchController findByTrainer Specialization B, trainerList.size(): " + trainerList.size());
        model.addAttribute("trainerList", trainerList);
        System.out.println("GymSearchController findByTrainer Specialization C");
        return "gym_search/trainerField";
    }

    @GetMapping("/gym/search/trainer/specialization")
    public String trainerFormView(Model model) {
        System.out.println("GET GET GymSearchController findByTrainer Specialization A");
        return "gym_search/trainerFieldForm";
    }
}
