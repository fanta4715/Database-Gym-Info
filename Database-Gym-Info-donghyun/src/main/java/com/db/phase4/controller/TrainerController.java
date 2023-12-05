package com.db.phase4.controller;

import com.db.phase4.dto.trainer.TrainerRegisterDto;
import com.db.phase4.dto.trainer.TrainerViewDto;
import com.db.phase4.service.TrainerService;
import com.db.phase4.service.UserService;

import java.util.List;

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
public class TrainerController {
    private final TrainerService trainerService;
    private final UserService userService;

    @PostMapping("/register-trainer")
    public String registerTrainer(@ModelAttribute TrainerRegisterDto trainerRegisterDto) {
        userService.registerTrainer(trainerRegisterDto);
        return "redirect:/user/"+trainerRegisterDto.getUserId()+"/gym/"+trainerRegisterDto.getGymId()+"/trainer";
    }
}
