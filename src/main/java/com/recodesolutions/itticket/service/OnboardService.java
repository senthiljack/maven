package com.recodesolutions.itticket.service;

import com.recodesolutions.itticket.dto.OnboardDto;
import com.recodesolutions.itticket.entity.Onboard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OnboardService {

    Onboard getById(Long id);
    Page<Onboard> getOnboardList(Pageable pageable);
    Onboard saveOnboard(OnboardDto OnboardDto);
    Onboard updateOnboard(OnboardDto OnboardDto);
    List<Onboard> getRevisionHistoryForOnboard(Long id);

    String downlaodPdf(Long id);
}
