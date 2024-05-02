package com.esliceyament.projectdemo.repository;

import com.esliceyament.projectdemo.model.PropsClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PropsRepository extends JpaRepository<PropsClient, Long>, JpaSpecificationExecutor<PropsClient> {
    List<PropsClient> findByClientId(Long clientId);
}
