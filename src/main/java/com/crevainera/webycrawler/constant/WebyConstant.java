package com.crevainera.webycrawler.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WebyConstant {

    DOCUMENT_ERROR_RETRIEVE("Error getting html page from web site"),
    CRAWLER_ERROR("%s at: %s"),
    SCRAPER_ERROR_HEADLINE("Error scraping headline"),
    SCRAPER_ERROR_TITLE("Error scraping headline's title"),
    SCRAPER_ERROR_LINK("Error scraping headline's link"),
    SCRAPER_ERROR_THUMB("Error scraping headline's thumb image");

    private String label;
}
