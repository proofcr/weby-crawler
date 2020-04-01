package com.crevainera.webycrawler.services;

import com.crevainera.webycrawler.exception.WebyException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.crevainera.webycrawler.constant.WebyConstant.DOCUMENT_ERROR_RETRIEVE;

@Component
@Slf4j
public class HtmlDocumentService {

    private String browserName;

    public HtmlDocumentService(@Value("${crawler.browser}") final String browserName) {
        log.info("Crawler's browser: " + browserName);
        this.browserName = browserName;
    }

    public Document getDocument(final String path) throws WebyException {
        try {
            Connection connection = Jsoup.connect(path);
            connection.userAgent(browserName);
            return connection.get();
        } catch (IOException e) {
            log.error(e.getStackTrace().toString());
            throw new WebyException(DOCUMENT_ERROR_RETRIEVE.name());
        }
    }
}
