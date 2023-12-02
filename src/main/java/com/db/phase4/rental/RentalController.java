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

    @GetMapping("/rental")
    public String index() {
        return "rental";
    }

    @GetMapping("/rental-search")
    public String rentalSearch(@RequestParam String gymId, Model model) {
        model.addAttribute("rentals", rentalService.rentalSearchById(gymId));
        return "rental-search";
    }


//            System.out.print("17. 대여물품 대여\n "
//            "INSERT INTO RENTS(User_id, Gym_id, Item_name) VALUES('" + user_id + "', '" + gym_id + "', '" + item_name + "')"

//            System.out.print("15. 운동기구 예약\n ");
//            sb.append("UPDATE USERS");
//		sb.append(" SET RESERVE_MACHINE_ID=" + machineId);
//		sb.append(" WHERE USER_ID=" + userId);

//        sb.append("UPDATE MACHINE");
//		sb.append(" SET STATE='non_reservable'");
//		sb.append(" WHERE MACHINE_ID=" + machineId);

//         System.out.print("16. 운동기구 사용\n ");
//         sb.append("UPDATE USERS");
//		sb.append(" SET USING_MACHINE_ID=" + machineId);
//		sb.append(" WHERE USER_ID=" + userId);

//        sb.append("UPDATE MACHINE");
//		sb.append(" SET STATE='" + newState + "'");
//		sb.append(" WHERE MACHINE_ID=" + machineId);
}
