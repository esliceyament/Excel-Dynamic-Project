package com.esliceyament.projectdemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    @Email
    private String email;
    private LocalDate dob;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<PropsClient> propsClient;
}

