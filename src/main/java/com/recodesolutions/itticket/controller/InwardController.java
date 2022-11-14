package com.recodesolutions.itticket.controller;

import com.recodesolutions.itticket.dto.InwardDto;
import com.recodesolutions.itticket.dto.RequestHeaderData;
import com.recodesolutions.itticket.entity.Inward;
import com.recodesolutions.itticket.service.InwardService;
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
@RequestMapping("api/inward/")
public class InwardController {

    private final InwardService inwardService;

    @Resource(name = "requestScopeHeaderData")
    private RequestHeaderData requestHeaderData;


    @GetMapping("find/{id}")
    public Inward getById(@PathVariable Long id) {
        return inwardService.getById(id);
    }


    @GetMapping("list")
    public Page<Inward> getInwardList(Pageable pageable) {
        return inwardService.getInwardList(pageable);
    }

    @PostMapping("save")
    public Inward saveInward(@RequestBody InwardDto inwardDto) {
        return inwardService.saveInward(inwardDto);
    }

    @PutMapping("save")
    public Inward updateInward(@RequestBody InwardDto inwardDto) {
        return inwardService.updateInward(inwardDto);
    }

    @GetMapping("history/{id}")
    public List<Inward> getRevisiontest(@PathVariable Long id) {
        return inwardService.getRevisionHistoryForInward(id);
    }

    @GetMapping("download/{id}")
    public String downloadPdf(@PathVariable Long id){
        return inwardService.downlaodPdf(id);
    }


}
