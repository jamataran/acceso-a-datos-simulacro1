package com.cev.aircev.service.mapper;

import com.cev.aircev.domain.*;
import com.cev.aircev.service.dto.TripulanteDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tripulante} and its DTO {@link TripulanteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TripulanteMapper extends EntityMapper<TripulanteDTO, Tripulante> {
    @Named("dniSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "dni", source = "dni")
    Set<TripulanteDTO> toDtoDniSet(Set<Tripulante> tripulante);
}
