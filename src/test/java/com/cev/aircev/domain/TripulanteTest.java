package com.cev.aircev.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cev.aircev.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TripulanteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tripulante.class);
        Tripulante tripulante1 = new Tripulante();
        tripulante1.setId(1L);
        Tripulante tripulante2 = new Tripulante();
        tripulante2.setId(tripulante1.getId());
        assertThat(tripulante1).isEqualTo(tripulante2);
        tripulante2.setId(2L);
        assertThat(tripulante1).isNotEqualTo(tripulante2);
        tripulante1.setId(null);
        assertThat(tripulante1).isNotEqualTo(tripulante2);
    }
}
