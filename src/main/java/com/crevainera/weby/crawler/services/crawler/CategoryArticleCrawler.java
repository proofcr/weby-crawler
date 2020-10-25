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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.crevainera.weby.crawler.config.ActiveMQConfiguration.ARTICLE_ID_MESSAGE_QUEUE;
import static com.crevainera.weby.crawler.constant.WebyConstant.CRAWLER_ERROR;

/**
 * Creates articles from headlines for a given category of a site and updates Articles table
 */
@Service
@Slf4j
public class CategoryArticleCrawler {

    public static final String HTTP_PROTOCOL = "http";

    private JmsTemplate jmsTemplate;
    private HtmlDocumentConnector documentFromHtml;
    private ArticleRepository articleRepository;
    private HeadlineListScraper headlineListScraper;

    @Autowired
    public CategoryArticleCrawler(final JmsTemplate jmsTemplate,
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
            log.debug("crawling category: " + category.getUrl());

            Document document = documentFromHtml.getDocument(category.getUrl());

            List<HeadLineDto> categoryHeadLineDtoList = headlineListScraper.getHeadLinesFromDocument(document, scrapRule);

            getNewHeadlines(categoryHeadLineDtoList).forEach(
                    headLine -> {
                        Optional<Article> article = getArticleInAllCategories(headLine.getUrl());

                        if (article.isPresent()) {
                            updateArticle(article.get(), category);
                        } else {
                            saveNewArticle(site, category, headLine);
                        }
                    }
            );
        } catch (WebyException e) {
            log.error(String.format(CRAWLER_ERROR.getMessage(), e.getMessage(), category.getUrl()));
        }
    }

    private Optional<Article> getArticleInAllCategories(final String url) {
        return Optional.ofNullable(articleRepository.findByUrl(url));
    }

    private List<HeadLineDto> getNewHeadlines(final List<HeadLineDto> headLineDtoList) {
        Slice<Article> articleSlice = articleRepository.findAll(PageRequest.of(1, headLineDtoList.size()));

        List<String> databaseUrls = articleSlice.stream().map(article -> article.getUrl())
                .collect(Collectors.toList());

        return headLineDtoList.stream()
                .filter(headLineDto -> !databaseUrls.contains(headLineDto.getUrl()))
                .collect(Collectors.toList());
    }

    private void updateArticle(final Article article, final Category category) {
        article.getLabelList().add(category.getLabel());
        articleRepository.save(article);
    }

    private void saveNewArticle(final Site site, final Category category, final HeadLineDto headLineDto) {
        Article article = new Article();
        article.setTitle(headLineDto.getTitle());
        article.setUrl(headLineDto.getUrl());
        getFullThumbUrl(site, headLineDto.getThumbUrl()).ifPresent(article::setThumbUrl);
        article.setScrapDate(new Date());
        article.setSite(site);

        article.getLabelList().add(category.getLabel());
        articleRepository.save(article);

        if (site.getScrapThumbEnabled() && StringUtils.isNotBlank(article.getThumbUrl())) {
            jmsTemplate.convertAndSend(ARTICLE_ID_MESSAGE_QUEUE, article.getId());
        }
    }

    private Optional<String> getFullThumbUrl(final Site site, final String url) {
        if (site.getScrapThumbEnabled() && (StringUtils.isNotBlank(url))) {
            if (url.startsWith(HTTP_PROTOCOL)) {
                return Optional.of(url);
            } else {
                return Optional.of(site.getUrl() + url);
            }
        }

        return Optional.empty();
    }
}