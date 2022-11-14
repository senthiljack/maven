package com.recodesolutions.itticket.controller;

import com.recodesolutions.itticket.entity.AccessoryType;
import com.recodesolutions.itticket.repository.AccessoryTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("api/accessory/")
public class AccessoryController {

    private final AccessoryTypeRepository accessoryTypeRepository;

    @GetMapping("all")
    List<AccessoryType> getAllAccessoryType(){
        return accessoryTypeRepository.findByIsActiveTrue(Sort.by(Sort.Direction.ASC, "name"));
    }

}
