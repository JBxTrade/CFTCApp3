package com.cftc.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cftc.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UnSubTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnSub.class);
        UnSub unSub1 = new UnSub();
        unSub1.setId(1L);
        UnSub unSub2 = new UnSub();
        unSub2.setId(unSub1.getId());
        assertThat(unSub1).isEqualTo(unSub2);
        unSub2.setId(2L);
        assertThat(unSub1).isNotEqualTo(unSub2);
        unSub1.setId(null);
        assertThat(unSub1).isNotEqualTo(unSub2);
    }
}
