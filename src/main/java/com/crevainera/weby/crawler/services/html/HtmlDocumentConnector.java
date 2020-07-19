package com.crevainera.weby.crawler.services.html;

import com.crevainera.weby.crawler.exception.WebyException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.crevainera.weby.crawler.constant.WebyConstant.DOCUMENT_ERROR_RETRIEVE;

@Component
@Slf4j
public class HtmlDocumentConnector {

    private String browserName;

    public HtmlDocumentConnector(@Value("${crawler.browser}") final String browserName) {
        log.debug("Crawler's browser: " + browserName);
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
