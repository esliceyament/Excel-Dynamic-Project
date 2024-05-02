package com.esliceyament.projectdemo.mapper;

import com.esliceyament.projectdemo.dto.PropsRequest;
import com.esliceyament.projectdemo.dto.PropsResponse;
import com.esliceyament.projectdemo.model.PropsClient;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class PropsMapper {

    public static PropsMapper INSTANCE = Mappers.getMapper(PropsMapper.class);

    @Mapping(source = "client.id", target = "clientId")
    public abstract PropsResponse toDto(PropsClient propsClient);

    @Mapping(source = "clientId", target = "client.id")
    public abstract PropsClient toEntity(PropsRequest propsRequest);
}
