package com.cev.aircev.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AeropuertoMapperTest {

    private AeropuertoMapper aeropuertoMapper;

    @BeforeEach
    public void setUp() {
        aeropuertoMapper = new AeropuertoMapperImpl();
    }
}
