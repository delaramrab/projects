package com.mapsa.dotin.repository;

import com.mapsa.dotin.model.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Person, Long> {
    Person findByUsername(String username);

}
