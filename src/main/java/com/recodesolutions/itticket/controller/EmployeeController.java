package com.recodesolutions.itticket.controller;

import com.recodesolutions.itticket.entity.EmpDetail;
import com.recodesolutions.itticket.service.EmpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("api/emp/")
public class EmployeeController {

    private final EmpService empService;

    @GetMapping("find/{id}")
    public EmpDetail getEmployeeById(@PathVariable Long id) {
        return empService.getEmployeeById(id);
    }
}
