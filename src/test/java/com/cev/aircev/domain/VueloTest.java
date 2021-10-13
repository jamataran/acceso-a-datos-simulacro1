package com.cev.aircev.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cev.aircev.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VueloTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vuelo.class);
        Vuelo vuelo1 = new Vuelo();
        vuelo1.setId(1L);
        Vuelo vuelo2 = new Vuelo();
        vuelo2.setId(vuelo1.getId());
        assertThat(vuelo1).isEqualTo(vuelo2);
        vuelo2.setId(2L);
        assertThat(vuelo1).isNotEqualTo(vuelo2);
        vuelo1.setId(null);
        assertThat(vuelo1).isNotEqualTo(vuelo2);
    }
}
