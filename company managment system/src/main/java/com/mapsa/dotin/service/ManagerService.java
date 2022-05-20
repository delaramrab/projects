package com.mapsa.dotin.service;


import com.mapsa.dotin.model.person.Manager;
import com.mapsa.dotin.model.person.Person;
import com.mapsa.dotin.repository.ManagerRepository;
import com.mapsa.dotin.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class ManagerService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    final ModelMapper modelMapper = new ModelMapper();


    ManagerRepository managerRepository;
    UserRepository userRepository;

    public Manager save(Manager manager){
        manager.setPassword(passwordEncoder.encode(manager.getPassword()));
        userRepository.save(mapObjectToCompanyUser(manager));
        return managerRepository.save(mapObject(manager));
    }

    public Manager mapObject(Manager manager){

        return modelMapper.map(manager,Manager.class);
    }

    public Person mapObjectToCompanyUser(Manager manager){
        return modelMapper.map(manager,Person.class);
    }

    public Manager findByUsername(String username){
        return managerRepository.findByUsername(username);
    }

    public List<Manager> getAll(){
        return managerRepository.findAll();
    }
}