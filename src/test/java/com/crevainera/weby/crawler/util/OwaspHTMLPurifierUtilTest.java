package com.crevainera.weby.crawler.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for {@link OwaspHTMLPurifierUtil}
 */
class OwaspHTMLPurifierUtilTest {

    @Test
    void whenHTMLIsProvidedShouldReturnOnlyAllowedTagsInTheRightFormat() {
        final String expected = "<p>La Secretaría de <b>Gobierno</b>  <i>municipal</i>.</p>";
        final String untrustedHTML = "<p style=\"text-align: justify;\">La Secretaría de <b style=\"asb\">Gobierno</b> "
                + "<script>alert('hola');</script> <i>municipal</i>.</p>";

        assertEquals(expected, OwaspHTMLPurifierUtil.purify(untrustedHTML));
    }

    // TODO add more test cases
}