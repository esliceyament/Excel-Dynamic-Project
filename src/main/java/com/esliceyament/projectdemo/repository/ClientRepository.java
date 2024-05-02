package com.esliceyament.projectdemo.repository;

import com.esliceyament.projectdemo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
    Optional<Client> findByEmail(String email);
}
