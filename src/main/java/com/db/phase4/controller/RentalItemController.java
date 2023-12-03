package com.db.phase4.controller;

import com.db.phase4.service.RentalItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class RentalItemController {
    private final RentalItemService rentalItemService;

}
