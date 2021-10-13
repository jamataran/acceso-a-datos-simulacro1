package com.cev.aircev.repository;

import com.cev.aircev.domain.Piloto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Piloto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PilotoRepository extends JpaRepository<Piloto, Long> {}
