package com.recodesolutions.itticket.controller;

import com.recodesolutions.itticket.dto.OutwardDto;
import com.recodesolutions.itticket.dto.RequestHeaderData;
import com.recodesolutions.itticket.entity.Outward;
import com.recodesolutions.itticket.service.OutwardService;
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
@RequestMapping("api/outward/")
public class OutwardController {

    private final OutwardService outwardService;

    @Resource(name = "requestScopeHeaderData")
    private RequestHeaderData requestHeaderData;


    @GetMapping("find/{id}")
    public Outward getById(@PathVariable Long id) {
        return outwardService.getById(id);
    }


    @GetMapping("list")
    public Page<Outward> getOutwardList(Pageable pageable) {
        return outwardService.getOutwardList(pageable);
    }

    @PostMapping("save")
    public Outward saveOutward(@RequestBody OutwardDto OutwardDto) {
        return outwardService.saveOutward(OutwardDto);
    }

    @PutMapping("save")
    public Outward updateOutward(@RequestBody OutwardDto OutwardDto) {
        return outwardService.updateOutward(OutwardDto);
    }

    @GetMapping("history/{id}")
    public List<Outward> getRevisiontest(@PathVariable Long id) {
        return outwardService.getRevisionHistoryForOutward(id);
    }

    @GetMapping("download/{id}")
    public String downloadPdf(@PathVariable Long id){
        return outwardService.downlaodPdf(id);
    }


}
