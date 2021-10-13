package com.cev.aircev.repository;

import com.cev.aircev.domain.Aeropuerto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Aeropuerto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AeropuertoRepository extends JpaRepository<Aeropuerto, Long> {}
