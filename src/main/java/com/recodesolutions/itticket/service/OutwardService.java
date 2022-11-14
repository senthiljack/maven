package com.recodesolutions.itticket.service;

import com.recodesolutions.itticket.dto.OutwardDto;
import com.recodesolutions.itticket.entity.Outward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OutwardService {

    Outward getById(Long id);
    Page<Outward> getOutwardList(Pageable pageable);
    Outward saveOutward(OutwardDto OutwardDto);
    Outward updateOutward(OutwardDto OutwardDto);
    List<Outward> getRevisionHistoryForOutward(Long id);

    String downlaodPdf(Long id);
}
