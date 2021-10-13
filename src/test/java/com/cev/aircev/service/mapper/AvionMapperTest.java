package com.cev.aircev.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AvionMapperTest {

    private AvionMapper avionMapper;

    @BeforeEach
    public void setUp() {
        avionMapper = new AvionMapperImpl();
    }
}
