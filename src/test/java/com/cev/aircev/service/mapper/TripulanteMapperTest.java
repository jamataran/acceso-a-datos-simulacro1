package com.cev.aircev.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TripulanteMapperTest {

    private TripulanteMapper tripulanteMapper;

    @BeforeEach
    public void setUp() {
        tripulanteMapper = new TripulanteMapperImpl();
    }
}
