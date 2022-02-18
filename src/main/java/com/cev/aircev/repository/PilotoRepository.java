package com.cev.aircev.repository;

import java.awt.print.Pageable;
import java.util.List;

import com.cev.aircev.domain.Piloto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Piloto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PilotoRepository extends JpaRepository<Piloto, Long> {

    List<Piloto> findAllByDni(String dni, Pageable pageable);

}
