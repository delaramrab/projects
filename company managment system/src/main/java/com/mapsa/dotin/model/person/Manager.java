package com.mapsa.dotin.model.person;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "managers")
@Component
@Getter
@Setter
public class Manager extends Person {

    private String firstName;
    private String lastName;

   @NotBlank(message = "You have to enter an email.")
    private String emailAddress;


    protected EmployeeRole employeeRole;
    @OneToMany
    private List<Employee> employees;

}
