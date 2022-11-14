package com.recodesolutions.itticket.service;

import com.recodesolutions.itticket.dto.RelievingDto;
import com.recodesolutions.itticket.entity.Relieving;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RelievingService {

    Relieving getById(Long id);
    Page<Relieving> getRelievingList(Pageable pageable);
    Relieving saveRelieving(RelievingDto RelievingDto);
    Relieving updateRelieving(RelievingDto RelievingDto);
    List<Relieving> getRevisionHistoryForRelieving(Long id);

    String downlaodPdf(Long id);
}
