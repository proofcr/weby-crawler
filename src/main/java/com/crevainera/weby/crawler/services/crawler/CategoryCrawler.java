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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.crevainera.weby.crawler.config.ActiveMQConfiguration.ARTICLE_ID_MESSAGE_QUEUE;
import static com.crevainera.weby.crawler.constant.WebyConstant.CRAWLER_ERROR;

/**
 * Creates articles from headlines for a given category of a site and updates Articles table
 */
@Service
@Slf4j
public class CategoryCrawler {

    public static final String HTTP_PROTOCOL = "http";

    private JmsTemplate jmsTemplate;
    private HtmlDocumentConnector documentFromHtml;
    private ArticleRepository articleRepository;
    private HeadlineListScraper headlineListScraper;

    @Autowired
    public CategoryCrawler(final JmsTemplate jmsTemplate,
                           final HtmlDocumentConnector documentFromHtml,
                           final ArticleRepository articleRepository,
                           final HeadlineListScraper headlineListScraper) {
        this.jmsTemplate = jmsTemplate;
        this.documentFromHtml = documentFromHtml;
        this.articleRepository = articleRepository;
        this.headlineListScraper = headlineListScraper;
    }

    public void crawlCategory(final Site site, final Category category) {
        ScrapRule scrapRule = category.getScrapRule();
        try {
            Document document = documentFromHtml.getDocument(category.getUrl());
            log.debug("crawling category: " + category.getUrl());

            for (HeadLineDto headLineDto : headlineListScraper.getHeadLinesFromDocument(document, scrapRule)) {

                Article article = articleRepository.findByUrl(headLineDto.getUrl());

                if (article == null) {
                    article = new Article();
                    article.setTitle(headLineDto.getTitle());
                    article.setUrl(headLineDto.getUrl());
                    article.setThumbUrl(getFullThumbUrl(site, headLineDto.getThumbUrl()));
                    article.setScrapDate(new Date());
                    article.setSite(site);
                    article.getLabelList().add(category.getLabel());
                    articleRepository.save(article);

                    if (site.getScrapThumbEnabled() && StringUtils.isNotBlank(article.getThumbUrl())) {
                        jmsTemplate.convertAndSend(ARTICLE_ID_MESSAGE_QUEUE, article.getId());
                    }

                } else if (!article.getLabelList().contains(category.getLabel())) {
                    article.getLabelList().add(category.getLabel());
                    articleRepository.save(article);
                } else {
                    break; // database is updated
                }
            }

        } catch (WebyException e) {
            log.error(String.format(CRAWLER_ERROR.getMessage(), e.getMessage(), category.getUrl()));
        }
    }

    private String getFullThumbUrl(final Site site, final String url) {
        String fullUrl = StringUtils.EMPTY;
        if (site.getScrapThumbEnabled() && (StringUtils.isNotBlank(url))) {
            if (url.startsWith(HTTP_PROTOCOL)) {
                fullUrl = url;
            } else {
                fullUrl = site.getUrl() + url;
            }
        }

        return fullUrl;
    }
}
