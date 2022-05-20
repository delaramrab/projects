package com.mapsa.dotin.repository;


import com.mapsa.dotin.model.person.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Manager findByUsername(String username);

    Optional<Manager> findById(Long id);
}
