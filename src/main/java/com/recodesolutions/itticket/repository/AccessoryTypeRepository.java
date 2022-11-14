package com.recodesolutions.itticket.repository;

import com.recodesolutions.itticket.entity.AccessoryType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessoryTypeRepository extends JpaRepository<AccessoryType, Long>, JpaSpecificationExecutor<AccessoryType> {
    List<AccessoryType> findByIsActiveTrue(Sort name);


}