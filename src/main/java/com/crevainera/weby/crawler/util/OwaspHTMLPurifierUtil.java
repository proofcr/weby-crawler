package com.crevainera.weby.crawler.util;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import java.util.Arrays;

/**
 * Owasp HTML purifier util
 */
public class OwaspHTMLPurifierUtil {

    public static String purify(final String untrustedHTML) {
        HtmlPolicyBuilder htmlPolicyBuilder = new HtmlPolicyBuilder();

        String[] tags = Arrays.stream(WebyBBCodeTag.values()).map(WebyBBCodeTag::getValue)
                .toArray(size -> new String[size]);

        PolicyFactory policy  = htmlPolicyBuilder.allowElements(tags).toFactory();

        return policy.sanitize(untrustedHTML);
    }
}
