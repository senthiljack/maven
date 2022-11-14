package com.recodesolutions.itticket.repository;

import com.recodesolutions.itticket.entity.InwardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InwardItemRepository extends JpaRepository<InwardItem, Long>, JpaSpecificationExecutor<InwardItem> {
}