package com.db.phase4.controller;

import com.db.phase4.service.MachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MachineController {
    private final MachineService machineService;

    @GetMapping("/user/{userId}/gym/{gymId}/machine/{machineId}/{reservation}")
    public String machineReservation(@PathVariable int gymId, @PathVariable int machineId, @PathVariable int userId, @PathVariable String reservation) {
        System.out.println("machineReservation : " + reservation);
        machineService.reserveMachine(gymId, machineId, userId, reservation);
        return "redirect:/user/" + userId + "/gym/" + gymId + "/machine";
    }
}
