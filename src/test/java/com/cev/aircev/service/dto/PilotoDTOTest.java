package com.cev.aircev.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cev.aircev.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PilotoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PilotoDTO.class);
        PilotoDTO pilotoDTO1 = new PilotoDTO();
        pilotoDTO1.setId(1L);
        PilotoDTO pilotoDTO2 = new PilotoDTO();
        assertThat(pilotoDTO1).isNotEqualTo(pilotoDTO2);
        pilotoDTO2.setId(pilotoDTO1.getId());
        assertThat(pilotoDTO1).isEqualTo(pilotoDTO2);
        pilotoDTO2.setId(2L);
        assertThat(pilotoDTO1).isNotEqualTo(pilotoDTO2);
        pilotoDTO1.setId(null);
        assertThat(pilotoDTO1).isNotEqualTo(pilotoDTO2);
    }
}
