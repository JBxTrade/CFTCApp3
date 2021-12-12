package com.cftc.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cftc.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LinkDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LinkData.class);
        LinkData linkData1 = new LinkData();
        linkData1.setId(1L);
        LinkData linkData2 = new LinkData();
        linkData2.setId(linkData1.getId());
        assertThat(linkData1).isEqualTo(linkData2);
        linkData2.setId(2L);
        assertThat(linkData1).isNotEqualTo(linkData2);
        linkData1.setId(null);
        assertThat(linkData1).isNotEqualTo(linkData2);
    }
}
