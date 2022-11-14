package com.recodesolutions.itticket.repository;

import com.recodesolutions.itticket.entity.EmpDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpDetailRepository extends JpaRepository<EmpDetail, Long> {

    Optional<EmpDetail> findByEmpId(Long empId);

}