package com.crevainera.weby.crawler.services;

import com.crevainera.weby.crawler.exception.WebyException;
import com.crevainera.weby.crawler.dto.HeadLineDto;
import com.crevainera.weby.crawler.entities.ScrapRule;
import com.crevainera.weby.crawler.services.headline.HeadlineScraperService;
import com.crevainera.weby.crawler.services.headline.HtmlDocumentScraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class HeadlineScraperServiceTest {

    public static final String SITE_DIRECTORY = "sites/";
    public static final String SITE_AHORALASFLORES_HTML = SITE_DIRECTORY + "ahoralasflores.html";
    public static final String SITE_NOTICIASLASFLORES_HTML = SITE_DIRECTORY + "noticiaslasflores.html";
    public static final String SITE_PLAY_RADIOS_HTML = SITE_DIRECTORY + "playradios.html";

    private static Collection<Object[]> scrapRuleAndExpectedListSize() {
        return Arrays.asList(new Object[][]{
                {
                        SITE_AHORALASFLORES_HTML,
                        "$input.getElementsByTag(\"article\")",
                        "$input.getElementsByTag(\"h1\").first().getElementsByTag(\"a\").first().textNodes().get(0).text()",
                        "$input.getElementsByTag(\"h1\").first().getElementsByTag(\"a\").first().attr(\"href\")",
                        "($input.getElementsByTag(\"img\").first()!=null) ? $input.getElementsByTag(\"img\").first().attr(\"data-src\") : \"\"",
                        14

                },
                {
                        SITE_NOTICIASLASFLORES_HTML,
                        "$input.getElementsByTag(\"article\")",
                        "$input.getElementsByTag(\"h2\").first().getElementsByTag(\"a\").first().textNodes().get(0).text()",
                        "$input.getElementsByTag(\"h2\").first().getElementsByTag(\"a\").first().attr(\"href\")",
                        "($input.getElementsByTag(\"img\").first()!=null) ? $input.getElementsByTag(\"img\").first().attr(\"src\") : \"\"",
                        24
                },
                {
                        SITE_PLAY_RADIOS_HTML,
                        "$input.getElementsByClass(\"td_module_wrap\")",
                        "$input.getElementsByTag(\"h3\").first().getElementsByTag(\"a\").first().text().intern()",
                        "$input.getElementsByTag(\"h3\").first().getElementsByTag(\"a\").first().attr(\"href\")",
                        "$input.getElementsByClass(\"td_module_wrap\").first().getElementsByClass(\"entry-thumb\").attr(\"data-img-url\")",
                        21
                }
        });
    }

    @ParameterizedTest
    @MethodSource("scrapRuleAndExpectedListSize")
    public void scrapedHeadlinesListSizeShouldMatchWithExpectedListSize(
            final String siteHTMLFilePath, final String headline, final String title, final String image,
            final String link, final Integer expectedResultListSize) throws WebyException {
        Document document = getDocument(siteHTMLFilePath);
        ScrapRule scrapRule = new ScrapRule();
        scrapRule.setHeadline(headline);
        scrapRule.setTitle(title);
        scrapRule.setLink(link);
        scrapRule.setImage(image);
        HeadlineScraperService headlineScraperService = new HeadlineScraperService(new HtmlDocumentScraper());

        List<HeadLineDto> list = headlineScraperService.scrap(document, scrapRule);

        assertNotNull(list);
        assertEquals(expectedResultListSize, list.size());
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