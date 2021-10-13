package com.cev.aircev.repository;

import java.util.Optional;

import com.cev.aircev.domain.Avion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Avion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvionRepository extends JpaRepository<Avion, Long> {

    Optional<Avion> findFirstByOrderByEdadDesc();


}
