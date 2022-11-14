package com.recodesolutions.itticket.controller;

import com.recodesolutions.itticket.dto.RelievingDto;
import com.recodesolutions.itticket.dto.RequestHeaderData;
import com.recodesolutions.itticket.entity.Relieving;
import com.recodesolutions.itticket.service.RelievingService;
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
@RequestMapping("api/relieving/")
public class RelievingController {

    private final RelievingService relievingService;

    @Resource(name = "requestScopeHeaderData")
    private RequestHeaderData requestHeaderData;


    @GetMapping("find/{id}")
    public Relieving getById(@PathVariable Long id) {
        return relievingService.getById(id);
    }


    @GetMapping("list")
    public Page<Relieving> getRelievingList(Pageable pageable) {
        return relievingService.getRelievingList(pageable);
    }

    @PostMapping("save")
    public Relieving saveRelieving(@RequestBody RelievingDto RelievingDto) {
        return relievingService.saveRelieving(RelievingDto);
    }

    @PutMapping("save")
    public Relieving updateRelieving(@RequestBody RelievingDto RelievingDto) {
        return relievingService.updateRelieving(RelievingDto);
    }

    @GetMapping("history/{id}")
    public List<Relieving> getRevisiontest(@PathVariable Long id) {
        return relievingService.getRevisionHistoryForRelieving(id);
    }

    @GetMapping("download/{id}")
    public String downloadPdf(@PathVariable Long id){
        return relievingService.downlaodPdf(id);
    }


}
