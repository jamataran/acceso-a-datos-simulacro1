package com.cev.aircev.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PilotoMapperTest {

    private PilotoMapper pilotoMapper;

    @BeforeEach
    public void setUp() {
        pilotoMapper = new PilotoMapperImpl();
    }
}
