package com.esliceyament.projectdemo.dto;

import lombok.Data;

@Data
public class PropsRequest {
    private String propKey;
    private String value;
    private Long clientId;
}

