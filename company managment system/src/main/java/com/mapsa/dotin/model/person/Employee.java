package com.mapsa.dotin.model.person;


import com.mapsa.dotin.model.leave.Leave;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "employees")
@Component
@Setter
@Getter
public class Employee extends Person {

    protected String firstName;

    protected String lastName;

 //   @NotBlank(message = "You have to enter an email.")
    @Email
    protected String emailAddress;

    @Enumerated(value = EnumType.STRING)
    protected EmployeeStatus status = EmployeeStatus.ON_WORK;

    //TODO: make employeeStatus change automatically to EmployeeStatus.ON_LEAVE on their accepted leave dates and in manager leave requests list show that whether the employee is ON_WORK or ON_LEAVE

    @OneToMany(mappedBy = "employee")
    protected List<Leave> leaveList;

   // @NotNull(message = "You have to choose a role.")
    private EmployeeRole employeeRole;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "manager_id")
    protected Manager manager;
}
