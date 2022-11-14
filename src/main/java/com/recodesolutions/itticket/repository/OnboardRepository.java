package com.recodesolutions.itticket.repository;

import com.recodesolutions.itticket.entity.Inward;
import com.recodesolutions.itticket.entity.Onboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnboardRepository extends JpaRepository<Onboard, Long>, RevisionRepository<Onboard,Long,Integer> {
    @Modifying
    @Query(value="update onboard set display_id=?1 where id=?2",nativeQuery = true)
    void setDisplayId(String displayId,Long inwardId);
}