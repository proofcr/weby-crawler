package com.crevainera.weby.crawler.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for {@link WebyBBCodeUtil}
 */
class WebyBBCodeUtilTest {

    @Test
    void whenHTMLTagsAreProvidedShouldTransformOnlyAllowedHTMLTagsToBbCodeTagsNotAllowedTagsAreRemoved() {
        final String expected = "[p]hello [b]would[/b][/p]";
        final String input = "<p>hello <script>alert('hello');</script><b>would</b></p>";

        assertEquals(expected, WebyBBCodeUtil.transformToBBCode(input));
    }

    // TODO add more test cases
}