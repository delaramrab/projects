package com.mapsa.dotin.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
@Getter
@Setter
@Component
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    protected Long id;

  //  @NotBlank(message = "You have to enter a username.")
    @Column(name = "username", unique = true, nullable = false)
    protected String username;

 //   @NotBlank(message = "You have to enter a password.")
 //   @Min(value = 6, message = "Your password must have at least 6 characters.")
    @Column(name = "password", nullable = false)
    protected String password;



}
