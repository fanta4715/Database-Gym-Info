package com.db.phase4.controller;

import com.db.phase4.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    @GetMapping("/user/{userId}/gym/{gymId}/rental-item/{itemName}/rental-request")
    public String rentalSearch(@PathVariable int userId, @PathVariable int gymId, @PathVariable String itemName, Model model) {
        model.addAttribute("gymId", gymId);
        model.addAttribute("itemName", itemName);
        rentalService.requestRental(userId, gymId, itemName);
        return "/user/" + userId + "/gym/" + gymId + "/rental-item";
    }
}
