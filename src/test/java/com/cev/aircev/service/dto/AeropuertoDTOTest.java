package com.cev.aircev.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cev.aircev.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AeropuertoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AeropuertoDTO.class);
        AeropuertoDTO aeropuertoDTO1 = new AeropuertoDTO();
        aeropuertoDTO1.setId(1L);
        AeropuertoDTO aeropuertoDTO2 = new AeropuertoDTO();
        assertThat(aeropuertoDTO1).isNotEqualTo(aeropuertoDTO2);
        aeropuertoDTO2.setId(aeropuertoDTO1.getId());
        assertThat(aeropuertoDTO1).isEqualTo(aeropuertoDTO2);
        aeropuertoDTO2.setId(2L);
        assertThat(aeropuertoDTO1).isNotEqualTo(aeropuertoDTO2);
        aeropuertoDTO1.setId(null);
        assertThat(aeropuertoDTO1).isNotEqualTo(aeropuertoDTO2);
    }
}
