package com.recodesolutions.itticket.controller;

import com.recodesolutions.itticket.dto.OnboardDto;
import com.recodesolutions.itticket.dto.RequestHeaderData;
import com.recodesolutions.itticket.entity.Onboard;
import com.recodesolutions.itticket.service.OnboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Transactional
@RequiredArgsConstructor
@RequestMapping("api/onboard/")
public class OnboardController {

    private final OnboardService onboardService;

    @Resource(name = "requestScopeHeaderData")
    private RequestHeaderData requestHeaderData;


    @GetMapping("find/{id}")
    public Onboard getById(@PathVariable Long id) {
        return onboardService.getById(id);
    }


    @GetMapping("list")
    public Page<Onboard> getOnboardList(Pageable pageable) {
        return onboardService.getOnboardList(pageable);
    }

    @PostMapping("save")
    public Onboard saveOnboard(@RequestBody OnboardDto OnboardDto) {
        return onboardService.saveOnboard(OnboardDto);
    }

    @PutMapping("save")
    public Onboard updateOnboard(@RequestBody OnboardDto OnboardDto) {
        return onboardService.updateOnboard(OnboardDto);
    }

    @GetMapping("history/{id}")
    public List<Onboard> getRevisiontest(@PathVariable Long id) {
        return onboardService.getRevisionHistoryForOnboard(id);
    }

    @GetMapping("download/{id}")
    public String downloadPdf(@PathVariable Long id){
        return onboardService.downlaodPdf(id);
    }


}
