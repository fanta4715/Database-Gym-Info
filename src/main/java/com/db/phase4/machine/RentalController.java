package com.db.phase4.machine;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RentalController {
    @GetMapping("/rental")
    public String index() {
        return "rental";
    }

//     System.out.print("4. 특정 GYM의 보유 장비 조회\n ");
//    "SELECT * FROM GYM G JOIN RENTAL_ITEM R ON G.Gym_id = R.Gym_id WHERE G.Name = '" + name + "'"

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
