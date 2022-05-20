package com.mapsa.dotin.repository;


import com.mapsa.dotin.model.leave.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    @Transactional
    @Modifying
    @Query(value = "update leaves set accepted=?2 where id=?1", nativeQuery = true)
    void updateStatus(@Param("id") Long id, @Param("status") Boolean status);

    List<Leave> findAllByEmployeeUsername(String username);

    Optional<Leave> findById(Long id);
}
