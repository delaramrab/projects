package com.mapsa.dotin.model.person;

import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Person")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @NotBlank(message = "You have to enter a username.")
    @Column(name = "username", unique = true, nullable = false)
    protected String username;

   @NotBlank(message = "You have to enter a password.")
  //  @Min(value = 4, message = "Your password must have at least 4 characters.")
    @Column(name = "password", nullable = false, length = 1000)
    protected String password;


    @Column(name="FIRST_NAME", length=50)
    private String firstName;

    @Column(name="LAST_NAME", length=50)
    private String lastName;


    @Temporal(TemporalType.DATE)
    private Date birthDate;


    @Column
    private String emailAddress;

    @Enumerated(value = EnumType.STRING)
    protected EmployeeRole employeeRole;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;


    public Person(String firstName, String lastName, String email,String username, String password, Collection<Role> roles) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.username=username;
        this.emailAddress = email;
        this.password = password;
        this.roles = roles;
    }


}
