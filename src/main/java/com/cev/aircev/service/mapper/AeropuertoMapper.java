package com.cev.aircev.service.mapper;

import com.cev.aircev.domain.*;
import com.cev.aircev.service.dto.AeropuertoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Aeropuerto} and its DTO {@link AeropuertoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AeropuertoMapper extends EntityMapper<AeropuertoDTO, Aeropuerto> {
    @Named("nombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    AeropuertoDTO toDtoNombre(Aeropuerto aeropuerto);
}
