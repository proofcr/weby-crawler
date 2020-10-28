package com.crevainera.weby.crawler.services.crawler;

import com.crevainera.weby.crawler.dto.HeadLineDto;
import com.crevainera.weby.crawler.entities.Article;
import com.crevainera.weby.crawler.entities.Category;
import com.crevainera.weby.crawler.entities.ScrapRule;
import com.crevainera.weby.crawler.entities.Site;
import com.crevainera.weby.crawler.exception.WebyException;
import com.crevainera.weby.crawler.repositories.ArticleRepository;
import com.crevainera.weby.crawler.services.html.HtmlDocumentConnector;
import com.crevainera.weby.crawler.services.scraper.HeadlineListScraper;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link CategoryArticleCrawler}
 */
class CategoryArticleCrawlerTest {

    @Mock
    private JmsTemplate jmsTemplate;
    @Mock
    private HtmlDocumentConnector documentFromHtml;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private HeadlineListScraper headlineListScraper;
    @Mock
    private Document document;
    @Mock
    private ScrapRule scrapRule;

    private Site site;
    private Category category;
    private CategoryArticleCrawler categoryArticleCrawler;


    @BeforeEach
    void setup() throws WebyException {
        MockitoAnnotations.initMocks(this);

        final String category1Url = "http://site.com/category1.html";

        site = new Site();
        site.setTitle("Site1");
        site.setScrapThumbEnabled(true);

        category = new Category();
        category.setUrl(category1Url);
        category.setSiteId(site.getId());
        category.setScrapRule(scrapRule);

        doReturn(document).when(documentFromHtml).getDocument(category1Url);
        doReturn(createCrawledHeadLineStubList()).when(headlineListScraper).getHeadLines(document, scrapRule);

        categoryArticleCrawler = new CategoryArticleCrawler(jmsTemplate, documentFromHtml,
                articleRepository, headlineListScraper);
    }

    @Test
    void whenScraperReturnsEightHeadlinesThereAreNotArticlesForTheCategoryShouldSaveEightNewArticles() {
        categoryArticleCrawler.crawlCategory(site, category);

        verify(articleRepository, times(7)).save(any(Article.class));
    }

    // TODO add more test

    private List<HeadLineDto> createCrawledHeadLineStubList() {
        List<HeadLineDto> headLines = new ArrayList<>();

        headLines.add(createHeadLineStubDto(1L));
        headLines.add(createHeadLineStubDto(2L));
        headLines.add(createHeadLineStubDto(3L));
        headLines.add(createHeadLineStubDto(5L));
        headLines.add(createHeadLineStubDto(6L));
        headLines.add(createHeadLineStubDto(7L));
        headLines.add(createHeadLineStubDto(8L));

        return headLines;
    }

    private HeadLineDto createHeadLineStubDto(final long id) {
        final long headLine1SiteId = id;
        final String headLine1Title = "Title" + id;
        final String headLine1Description = "Description" + id;
        final String headLine1ThumbUrl = "http://site.com/thumbUrl" + id + ".jpg";
        final String headLine1ArticleUrl = "http://site.com/article" + id + ".html";

        HeadLineDto headLineDto = new HeadLineDto();
        headLineDto.setId(headLine1SiteId);
        headLineDto.setTitle(headLine1Title);
        headLineDto.setDescription(headLine1Description);
        headLineDto.setThumbUrl(headLine1ThumbUrl);
        headLineDto.setUrl(headLine1ArticleUrl);

        return headLineDto;
    }

}