package com.esliceyament.projectdemo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientResponse {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private LocalDate dob;
}

