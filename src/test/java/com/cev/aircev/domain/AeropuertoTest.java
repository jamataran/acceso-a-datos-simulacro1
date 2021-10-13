package com.cev.aircev.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cev.aircev.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AeropuertoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aeropuerto.class);
        Aeropuerto aeropuerto1 = new Aeropuerto();
        aeropuerto1.setId(1L);
        Aeropuerto aeropuerto2 = new Aeropuerto();
        aeropuerto2.setId(aeropuerto1.getId());
        assertThat(aeropuerto1).isEqualTo(aeropuerto2);
        aeropuerto2.setId(2L);
        assertThat(aeropuerto1).isNotEqualTo(aeropuerto2);
        aeropuerto1.setId(null);
        assertThat(aeropuerto1).isNotEqualTo(aeropuerto2);
    }
}
