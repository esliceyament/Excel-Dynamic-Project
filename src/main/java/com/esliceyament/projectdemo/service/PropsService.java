package com.esliceyament.projectdemo.service;

import com.esliceyament.projectdemo.dto.PropsRequest;
import com.esliceyament.projectdemo.dto.PropsResponse;
import com.esliceyament.projectdemo.mapper.PropsMapper;
import com.esliceyament.projectdemo.model.Client;
import com.esliceyament.projectdemo.model.PropsClient;
import com.esliceyament.projectdemo.repository.PropsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
@Service@RequiredArgsConstructor
public class PropsService {
    private final PropsRepository propsRepository;

    public void addProp(PropsRequest propsRequest) {
        propsRepository.save(PropsMapper.INSTANCE.toEntity(propsRequest));
    }

    public List<PropsResponse> getAllProps() {
        return propsRepository.findAll().stream().map(PropsMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    public void addProps(List<PropsRequest> propsRequests, Client client) {
        List<PropsClient> existingProps = propsRepository.findByClientId(client.getId());
        Map<String, PropsClient> existingPropsMap = existingProps.stream()
                .collect(Collectors.toMap(PropsClient::getPropKey, Function.identity()));
        List<PropsClient> propsToSave = new ArrayList<>();
        for (PropsRequest propsRequest : propsRequests) {
            if (existingPropsMap.containsKey(propsRequest.getPropKey())) {
                PropsClient existingProp = existingPropsMap.get(propsRequest.getPropKey());
                existingProp.setValue(propsRequest.getValue());
                propsToSave.add(existingProp);
        }
            else {
                PropsClient newProp = PropsMapper.INSTANCE.toEntity(propsRequest);
                newProp.setClient(client);
                propsToSave.add(newProp);
        }
        }

        propsRepository.saveAll(propsToSave);
    }
}