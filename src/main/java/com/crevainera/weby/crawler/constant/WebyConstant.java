package com.crevainera.weby.crawler.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WebyConstant {

    DOCUMENT_ERROR_RETRIEVE("Error getting html page from web site"),
    CRAWLER_ERROR("%s at: %s"),
    SCRAPER_DOCUMENT_ERROR("Error scraping article from list at %"),
    SCRAPER_ARTICLE_PART_ERROR("Error scraping article part from list at %"),
    IMAGE_SERVICE_IMAGE_NAME("Error resizing image"),
    ARTICLE_SERVICE_EXCEPTION("Article service error"),
    NOT_FOUND_URL("Not found URL"),
    MALFORMED_URL("Malformed URL");

    private String message;
}
