package com.cev.aircev.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cev.aircev.domain.Vuelo;

/**
 * Spring Data SQL repository for the Vuelo entity.
 */
@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Long> {
    @Query(
        value = "select distinct vuelo from Vuelo vuelo left join fetch vuelo.tripulacions",
        countQuery = "select count(distinct vuelo) from Vuelo vuelo"
    )
    Page<Vuelo> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct vuelo from Vuelo vuelo left join fetch vuelo.tripulacions")
    List<Vuelo> findAllWithEagerRelationships();

    @Query("select vuelo from Vuelo vuelo left join fetch vuelo.tripulacions where vuelo.id =:id")
    Optional<Vuelo> findOneWithEagerRelationships(@Param("id") Long id);

    Page<Vuelo> findAllByPilotoDni(String dniPiloto, Pageable pageable);

    long countVuelosByTripulacionsDni(String dniTripulante);

}
