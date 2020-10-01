package com.crevainera.weby.crawler.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Weby BBCode tags
 */
@AllArgsConstructor
@Getter
public enum WebyBBCodeTag {

    PARAGRAPH("p"),
    BOLD("b"),
    ITALIC("i"),
    STRIKETHROUGH("s"),
    IMAGE("img"),
    LINK("url");

    private String value;
}
