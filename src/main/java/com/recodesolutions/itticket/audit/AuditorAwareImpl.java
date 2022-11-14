package com.recodesolutions.itticket.audit;

import com.recodesolutions.itticket.dto.RequestHeaderData;
import org.springframework.data.domain.AuditorAware;

import javax.annotation.Resource;
import java.util.Optional;


public class AuditorAwareImpl implements AuditorAware<Long> {

    @Resource(name = "requestScopeHeaderData")
    private RequestHeaderData requestHeaderData;

    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.ofNullable(requestHeaderData.getId());
    }
}
