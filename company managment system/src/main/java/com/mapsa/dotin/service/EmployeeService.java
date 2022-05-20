package com.mapsa.dotin.service;


import com.mapsa.dotin.model.person.Employee;
import com.mapsa.dotin.model.person.Person;
import com.mapsa.dotin.repository.EmployeeRepository;
import com.mapsa.dotin.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

  final ModelMapper modelMapper = new ModelMapper();


     EmployeeRepository employeeRepository;
    UserRepository userRepository;


    public Employee save(Employee employee){
      employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        userRepository.save(mapObjectToCompanyUser(employee));
        return employeeRepository.save(mapObject(employee));
    }

    public List<Employee> getAll(){
        return employeeRepository.findAll();
    }

    public Employee mapObject(Employee employeeDto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(employeeDto,Employee.class);
    }

    public Person mapObjectToCompanyUser(Employee employeeDto){
        return modelMapper.map(employeeDto,Person.class);
    }

    public Employee findByUsername(String username){
        return employeeRepository.findByUsername(username);
    }







    public List<Person> userGetAll() {

        return new ArrayList<>(
                userRepository.findAll());
    }




    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }


    public void delete(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        employeeRepository.delete(employee);
    }



}
