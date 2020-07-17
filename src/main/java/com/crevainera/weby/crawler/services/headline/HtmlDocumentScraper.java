package com.crevainera.weby.crawler.services.headline;

import com.crevainera.weby.crawler.exception.WebyException;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import static com.crevainera.weby.crawler.constant.WebyConstant.*;

@Service
@Slf4j
public class HtmlDocumentScraper {

    private static final String INPUT_BINDING = "$input";

    public Elements getArticleElements(final Document document, final String scriptText) throws WebyException {
       try {
        return (Elements) createGroovyShell(document).evaluate(scriptText);
        } catch (Exception e) {
            log.error(String.format(SCRAPER_DOCUMENT_ERROR.name(), scriptText));
            throw new WebyException(String.format(SCRAPER_DOCUMENT_ERROR.name(), scriptText));
        }
    }

    public String getPlainText(final Element article, final String scriptText) {
        try {
            return removeHTMLTags((String) createGroovyShell(article).evaluate(scriptText));
        } catch (Exception e) {
            log.error(String.format(SCRAPER_ARTICLE_PART_ERROR.name(), scriptText));
        }
        return null;
    }

    private GroovyShell createGroovyShell(final Object document) {
        final Binding documentBinding = new Binding();
        documentBinding.setProperty(INPUT_BINDING, document);
        return new GroovyShell(documentBinding);
    }

    private String removeHTMLTags(final String html) {
        return Jsoup.clean(html, Whitelist.none());
    }
}
