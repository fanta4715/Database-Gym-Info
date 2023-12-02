package com.db.phase4.controller;

import com.db.phase4.dto.gym.GymViewDto;
import com.db.phase4.dto.review.ReviewViewDto;
import com.db.phase4.service.GymSearchService;
import com.db.phase4.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GymSearchController {
    private final GymSearchService gymSearchService;

    //회원의 수가 특정 인원 수 미만인 gym 정보 조회
    @GetMapping("/gym/search/num/{numOfPeople}")
    public String numResultView(@PathVariable String numOfPeople, Model model) {
        System.out.println("GymSearchController A");
        List<GymViewDto> gymList = gymSearchService.findByNumOfPeople(numOfPeople);
        System.out.println("GymSearchController B, gymList.size(): " + gymList.size());
        model.addAttribute("gymList", gymList);
        System.out.println("GymSearchController C");
        return "gym_search/numOfPeople";
    }
}
