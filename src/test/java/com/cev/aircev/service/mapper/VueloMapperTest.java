package com.cev.aircev.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VueloMapperTest {

    private VueloMapper vueloMapper;

    @BeforeEach
    public void setUp() {
        vueloMapper = new VueloMapperImpl();
    }
}
