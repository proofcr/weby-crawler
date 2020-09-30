package com.crevainera.weby.crawler.util;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Weby BBCode util
 */
public class WebyBBCodeUtil {

    public static String transformToBBCode(final String html) {
        String bbBode = html;
        for (WebyBBCodeTag tag : WebyBBCodeTag.values()) {
            String lowerCaseTag = tag.getValue().toLowerCase();
            bbBode = bbBode.replaceAll("<" + lowerCaseTag + ">", "[" + lowerCaseTag + "]");
            bbBode = bbBode.replaceAll("</" + lowerCaseTag + ">", "[/" + lowerCaseTag + "]");
        }

        return Jsoup.clean(bbBode, Whitelist.none());
    }

}
