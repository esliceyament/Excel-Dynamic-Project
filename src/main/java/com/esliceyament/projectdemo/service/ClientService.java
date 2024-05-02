package com.esliceyament.projectdemo.service;

import com.esliceyament.projectdemo.dto.ClientRequest;
import com.esliceyament.projectdemo.dto.ClientResponse;
import com.esliceyament.projectdemo.exception.ExistingClient;
import com.esliceyament.projectdemo.mapper.ClientMapper;
import com.esliceyament.projectdemo.model.Client;
import com.esliceyament.projectdemo.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client addClient(ClientRequest clientRequest) {
        Optional<Client> clientOptional = clientRepository.findByEmail(clientRequest.getEmail());
        if (clientOptional.isEmpty()){
            return clientRepository.save(ClientMapper.INSTANCE.toEntity(clientRequest));
        }
        else {
            throw new ExistingClient("Client with this email exists!");
        }
    }

    public List<ClientResponse> getAllClients() {
        return clientRepository.findAll().stream().map(ClientMapper.INSTANCE::toDto).collect(Collectors.toList());
    }
}


