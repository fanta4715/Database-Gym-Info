package com.db.phase4.machine;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MachineController {
    private final MachineService machineService;

    @GetMapping("/machine-search")
    public String machineSearch(@RequestParam String gymId, @RequestParam String userId,Model model) {
        model.addAttribute("machines", machineService.machineSearchById(gymId));
        return "machine-search";
    }

//            System.out.print("15. 운동기구 예약\n ");
//            sb.append("UPDATE USERS");
//		sb.append(" SET RESERVE_MACHINE_ID=" + machineId);
//		sb.append(" WHERE USER_ID=" + userId);

    //        sb.append("UPDATE MACHINE");
//		sb.append(" SET STATE='non_reservable'");
//		sb.append(" WHERE MACHINE_ID=" + machineId);
    @GetMapping("/machine-reserve")
    public String machineReserve(@RequestParam String gymId, @RequestParam String machineId, @RequestParam String userId) {
        machineService.reserveMachine(gymId, machineId, userId);
        return "redirect:/machine-search?gymId=" + gymId + "&userId=" + userId;
    }

//         System.out.print("16. 운동기구 사용\n ");
//         sb.append("UPDATE USERS");
//		sb.append(" SET USING_MACHINE_ID=" + machineId);
//		sb.append(" WHERE USER_ID=" + userId);

    //        sb.append("UPDATE MACHINE");
//		sb.append(" SET STATE='" + newState + "'");
//		sb.append(" WHERE MACHINE_ID=" + machineId);
    @GetMapping("/machine-request")
    public String machineRequest(@RequestParam String gymId, @RequestParam String machineId, @RequestParam String userId) {
        machineService.requestMachine(gymId, machineId, userId);
        return "redirect:/machine-search?gymId=" + gymId + "&userId=" + userId;
    }
}
