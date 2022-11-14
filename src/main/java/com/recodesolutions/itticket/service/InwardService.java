package com.recodesolutions.itticket.service;

import com.recodesolutions.itticket.constants.ApplicationConstants;
import com.recodesolutions.itticket.dto.InwardDto;
import com.recodesolutions.itticket.entity.Inward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public interface InwardService {

    Inward getById(Long id);
    Page<Inward> getInwardList(Pageable pageable);
    Inward saveInward(InwardDto inwardDto);
    Inward updateInward(InwardDto inwardDto);
    List<Inward> getRevisionHistoryForInward(Long id);

    String downlaodPdf(Long id);
}
