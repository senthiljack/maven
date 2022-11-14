package com.recodesolutions.itticket.repository;

import com.recodesolutions.itticket.entity.Inward;
import com.recodesolutions.itticket.entity.Outward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutwardRepository extends JpaRepository<Outward, Long>, RevisionRepository<Outward,Long,Integer> {
    @Modifying
    @Query(value="update outward set display_id=?1 where id=?2",nativeQuery = true)
    void setDisplayId(String displayId,Long inwardId);
}