package com.db.phase4.rental;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    @GetMapping("/my-gym")
    public String index() {
        return "my-gym";
    }

    @GetMapping("/rental-search")
    public String rentalSearch(@RequestParam String gymId, Model model) {
        model.addAttribute("rentals", rentalService.rentalSearchById(gymId));
        return "rental-search";
    }

    //            System.out.print("17. 대여물품 대여\n "
//            "INSERT INTO RENTS(User_id, Gym_id, Item_name) VALUES('" + user_id + "', '" + gym_id + "', '" + item_name + "')"
    @GetMapping("/rental-request")
    public String rentalSearch(@RequestParam String gymId, @RequestParam String itemName,Model model) {
//        model.addAttribute("rentals", rentalService.(gymId, itemName));
        return "rental-request";
    }
}
