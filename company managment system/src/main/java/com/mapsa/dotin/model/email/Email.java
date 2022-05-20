package com.mapsa.dotin.model.email;

import com.mapsa.dotin.model.person.Person;
import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.List;



@Getter
@Setter
@Component
public class Email  {

    @NotBlank(message = "Email must have a sender.")
    @javax.validation.constraints.Email
    private String from;

    @NotBlank(message = "Email must have a receiver.")
    @javax.validation.constraints.Email
    private String to;

    private List<String> cc;

    private List<String> bcc;

    private String subject = "no subject";

    @NotBlank(message = "Email must have a body.")
    private String body;

}
