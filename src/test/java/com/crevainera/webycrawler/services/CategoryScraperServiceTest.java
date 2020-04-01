package com.crevainera.webycrawler.services;

import com.crevainera.webycrawler.dto.HeadLineDto;
import com.crevainera.webycrawler.entities.ScrapRule;
import com.crevainera.webycrawler.exception.WebyException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class CategoryScraperServiceTest {

    public static final String SITE_DIRECTORY = "sites/";
    public static final String SITE_NOTICIASLASFLORES_HTML = SITE_DIRECTORY + "noticiaslasflores.html";
    public static final String SITE_AHORALASFLORES_HTML = SITE_DIRECTORY + "ahoralasflores.html";
    public static final String SITE_PLAY_RADIOS_HTML = SITE_DIRECTORY + "playradios.html";

    @Test
    public void scrapHtmlDocumentWhenCategoryIsAhoraLasFlores() throws WebyException {
        Document document = getDocument(SITE_AHORALASFLORES_HTML);

        ScrapRule scrapRule = new ScrapRule();
        scrapRule.setHeadline("$input.getElementsByTag(\"article\")");
        scrapRule.setTitle("$input.getElementsByTag(\"h1\").first().getElementsByTag(\"a\").first().textNodes().get(0).text()");
        scrapRule.setImage("$input.getElementsByTag(\"h1\").first().getElementsByTag(\"a\").first().attr(\"href\")");
        scrapRule.setLink("($input.getElementsByTag(\"img\").first()!=null) ? $input.getElementsByTag(\"img\").first().attr(\"data-src\") : \"\"");

        CategoryScraperService categoryScraperService = new CategoryScraperService();
        List<HeadLineDto> list = categoryScraperService.scrap(document, scrapRule);
        assertNotNull(list);
        assertEquals(14, list.size());
    }

    @Test
    public void scrapHtmlDocumentWhenCategoryIsNoticiasLasFlores() throws WebyException {
        Document document = getDocument(SITE_NOTICIASLASFLORES_HTML);

        ScrapRule scrapRule = new ScrapRule();
        scrapRule.setHeadline("$input.getElementsByTag(\"article\")");
        scrapRule.setTitle("$input.getElementsByTag(\"h2\").first().getElementsByTag(\"a\").first().textNodes().get(0).text()");
        scrapRule.setImage("$input.getElementsByTag(\"h2\").first().getElementsByTag(\"a\").first().attr(\"href\")");
        scrapRule.setLink("($input.getElementsByTag(\"img\").first()!=null) ? $input.getElementsByTag(\"img\").first().attr(\"src\") : \"\"");

        CategoryScraperService categoryScraperService = new CategoryScraperService();
        List<HeadLineDto> list = categoryScraperService.scrap(document, scrapRule);
        assertNotNull(list);
        assertEquals(24, list.size());
    }

    @Test
    public void scrapHtmlDocumentWhenCategoryIsPlayRadios() throws WebyException {
        Document document = getDocument(SITE_PLAY_RADIOS_HTML);

        ScrapRule scrapRule = new ScrapRule();
        scrapRule.setHeadline("$input.getElementsByClass(\"td_module_wrap\")");
        scrapRule.setTitle("$input.getElementsByTag(\"h3\").first().getElementsByTag(\"a\").first().text().intern()");
        scrapRule.setLink("$input.getElementsByTag(\"h3\").first().getElementsByTag(\"a\").first().attr(\"href\")");
        scrapRule.setImage("$input.getElementsByClass(\"td_module_wrap\").first().getElementsByClass(\"entry-thumb\").attr(\"data-img-url\")");

        CategoryScraperService categoryScraperService = new CategoryScraperService();
        List<HeadLineDto> list = categoryScraperService.scrap(document, scrapRule);
        assertNotNull(list);
        assertEquals(21, list.size());
    }

    public Document getDocument(final String path) {
        URL urlPath = ClassLoader.getSystemResource(path);
        try {
            File input = new File(urlPath.toURI());
            return Jsoup.parse(input, "UTF-8");
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}