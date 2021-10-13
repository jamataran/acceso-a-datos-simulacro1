package com.cev.aircev.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.cev.aircev.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VueloDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VueloDTO.class);
        VueloDTO vueloDTO1 = new VueloDTO();
        vueloDTO1.setId(1L);
        VueloDTO vueloDTO2 = new VueloDTO();
        assertThat(vueloDTO1).isNotEqualTo(vueloDTO2);
        vueloDTO2.setId(vueloDTO1.getId());
        assertThat(vueloDTO1).isEqualTo(vueloDTO2);
        vueloDTO2.setId(2L);
        assertThat(vueloDTO1).isNotEqualTo(vueloDTO2);
        vueloDTO1.setId(null);
        assertThat(vueloDTO1).isNotEqualTo(vueloDTO2);
    }
}
