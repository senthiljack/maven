package com.recodesolutions.itticket.service.impl;

import com.recodesolutions.itticket.constants.ErrorCode;
import com.recodesolutions.itticket.constants.ErrorMessages;
import com.recodesolutions.itticket.entity.EmpDetail;
import com.recodesolutions.itticket.exception.ReITException;
import com.recodesolutions.itticket.repository.EmpDetailRepository;
import com.recodesolutions.itticket.service.EmpService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {

    private final EmpDetailRepository empDetailRepository;

    @Cacheable("employeeId")
    public EmpDetail getEmployeeById(Long id) {
        return empDetailRepository.findByEmpId(id).orElseThrow(() -> {
            throw new ReITException(ErrorCode.EMP_NOT_FOUND, ErrorMessages.EMP_NOT_FOUND);
        });
    }
}
