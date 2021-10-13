package com.cev.aircev.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cev.aircev.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TripulanteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TripulanteDTO.class);
        TripulanteDTO tripulanteDTO1 = new TripulanteDTO();
        tripulanteDTO1.setId(1L);
        TripulanteDTO tripulanteDTO2 = new TripulanteDTO();
        assertThat(tripulanteDTO1).isNotEqualTo(tripulanteDTO2);
        tripulanteDTO2.setId(tripulanteDTO1.getId());
        assertThat(tripulanteDTO1).isEqualTo(tripulanteDTO2);
        tripulanteDTO2.setId(2L);
        assertThat(tripulanteDTO1).isNotEqualTo(tripulanteDTO2);
        tripulanteDTO1.setId(null);
        assertThat(tripulanteDTO1).isNotEqualTo(tripulanteDTO2);
    }
}
