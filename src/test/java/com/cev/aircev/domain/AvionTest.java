package com.cev.aircev.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cev.aircev.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AvionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Avion.class);
        Avion avion1 = new Avion();
        avion1.setId(1L);
        Avion avion2 = new Avion();
        avion2.setId(avion1.getId());
        assertThat(avion1).isEqualTo(avion2);
        avion2.setId(2L);
        assertThat(avion1).isNotEqualTo(avion2);
        avion1.setId(null);
        assertThat(avion1).isNotEqualTo(avion2);
    }
}
