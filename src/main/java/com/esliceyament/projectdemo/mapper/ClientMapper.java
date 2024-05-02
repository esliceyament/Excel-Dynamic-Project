package com.esliceyament.projectdemo.mapper;

import com.esliceyament.projectdemo.dto.ClientRequest;
import com.esliceyament.projectdemo.dto.ClientResponse;
import com.esliceyament.projectdemo.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ClientMapper {
    public static ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    public abstract ClientResponse toDto(Client client);

    public abstract Client toEntity(ClientRequest clientRequest);
}
