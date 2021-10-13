package com.cev.aircev.service.mapper;

import com.cev.aircev.domain.*;
import com.cev.aircev.service.dto.VueloDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vuelo} and its DTO {@link VueloDTO}.
 */
@Mapper(componentModel = "spring", uses = { AvionMapper.class, AeropuertoMapper.class, PilotoMapper.class, TripulanteMapper.class })
public interface VueloMapper extends EntityMapper<VueloDTO, Vuelo> {
    @Mapping(target = "avion", source = "avion", qualifiedByName = "matricula")
    @Mapping(target = "origen", source = "origen", qualifiedByName = "nombre")
    @Mapping(target = "destino", source = "destino", qualifiedByName = "nombre")
    @Mapping(target = "piloto", source = "piloto", qualifiedByName = "dni")
    @Mapping(target = "tripulacions", source = "tripulacions", qualifiedByName = "dniSet")
    VueloDTO toDto(Vuelo s);

    @Mapping(target = "removeTripulacion", ignore = true)
    Vuelo toEntity(VueloDTO vueloDTO);
}
