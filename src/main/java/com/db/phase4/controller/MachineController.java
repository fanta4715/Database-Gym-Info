package com.db.phase4.controller;

import com.db.phase4.service.MachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MachineController {
    private final MachineService machineService;

    @PostMapping("/user/{userId}/gym/{gymId}/machine/{machineId}/reservation")
    public String machineReservation(@RequestParam String gymId, @RequestParam String machineId, @RequestParam String userId) {
        machineService.reserveMachine(gymId, machineId, userId);
        return "redirect:/user/" + userId + "/gym/" + gymId + "/machine";
    }
}
