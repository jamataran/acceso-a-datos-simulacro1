package com.cev.aircev.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cev.aircev.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AvionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvionDTO.class);
        AvionDTO avionDTO1 = new AvionDTO();
        avionDTO1.setId(1L);
        AvionDTO avionDTO2 = new AvionDTO();
        assertThat(avionDTO1).isNotEqualTo(avionDTO2);
        avionDTO2.setId(avionDTO1.getId());
        assertThat(avionDTO1).isEqualTo(avionDTO2);
        avionDTO2.setId(2L);
        assertThat(avionDTO1).isNotEqualTo(avionDTO2);
        avionDTO1.setId(null);
        assertThat(avionDTO1).isNotEqualTo(avionDTO2);
    }
}
