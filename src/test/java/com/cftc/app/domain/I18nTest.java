package com.cftc.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cftc.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class I18nTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(I18n.class);
        I18n i18n1 = new I18n();
        i18n1.setId(1L);
        I18n i18n2 = new I18n();
        i18n2.setId(i18n1.getId());
        assertThat(i18n1).isEqualTo(i18n2);
        i18n2.setId(2L);
        assertThat(i18n1).isNotEqualTo(i18n2);
        i18n1.setId(null);
        assertThat(i18n1).isNotEqualTo(i18n2);
    }
}
