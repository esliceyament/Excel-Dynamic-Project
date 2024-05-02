package com.esliceyament.projectdemo.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="client_properties", uniqueConstraints={
        @UniqueConstraint(columnNames = {"client_id", "prop_key"})
})
public class PropsClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prop_key")
    private String propKey;
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_id")
    private Client client;

}

