package com.crevainera.weby.crawler.services;

import com.crevainera.weby.crawler.dto.HeadLineDto;
import com.crevainera.weby.crawler.entities.Article;
import com.crevainera.weby.crawler.entities.Category;
import com.crevainera.weby.crawler.entities.ScrapRule;
import com.crevainera.weby.crawler.entities.Site;
import com.crevainera.weby.crawler.exception.WebyException;
import com.crevainera.weby.crawler.repositories.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import static com.crevainera.weby.crawler.constant.WebyConstant.*;

@Service
@Slf4j
public class CategoryCrawlerService {

    public static final String HTTP_PROTOCOL = "http";

    private CategoryScraperService scrapService;
    private HtmlDocumentService documentFromHtml;
    private ArticleRepository articleRepository;
    private ThumbService thumbService;

    @Autowired
    public CategoryCrawlerService(final ThumbService thumbService,
                                  final CategoryScraperService scrapService,
                                  final HtmlDocumentService documentFromHtml,
                                  final ArticleRepository articleRepository) {
        this.thumbService = thumbService;
        this.scrapService = scrapService;
        this.documentFromHtml = documentFromHtml;
        this.articleRepository = articleRepository;
    }

    public void crawlScrapAndSave(final Site site) {
        log.info("crawlScrapAndSave site: " + site.getUrl() + " (" + site.getTitle()+ ")");

        site.getCategoryList().forEach(category -> {
            if (category.getEnabled()) {
                crawlScrapAndSave(site, category);
            }
        });
    }

    private void crawlScrapAndSave(final Site site, final Category category) {
        ScrapRule scrapRule = category.getScrapRule();
        try {
            Document document = documentFromHtml.getDocument(category.getUrl());
            log.info("crawling category: " + category.getUrl());

            for (HeadLineDto headLineDto : scrapService.scrap(document, scrapRule)) {

                Article articleStored = articleRepository.findByUrl(headLineDto.getUrl());

                if (articleStored == null) {
                    Article article = new Article();
                    article.setTitle(headLineDto.getTitle());
                    article.setUrl(headLineDto.getUrl());

                    if (site.getScrapThumbEnabled() && (StringUtils.isNotBlank(headLineDto.getThumbUrl()))) {
                        if (headLineDto.getThumbUrl().startsWith(HTTP_PROTOCOL)) {
                            article.setThumbUrl(headLineDto.getThumbUrl());
                        } else {
                            article.setThumbUrl(site.getUrl() + headLineDto.getThumbUrl());
                        }
                        try {
                            article.setThumb(thumbService.resize(new URL(article.getThumbUrl())));
                        } catch (MalformedURLException e) {
                            log.error(MALFORMED_URL.getLabel(), e.getStackTrace());
                        } catch (WebyException e) {
                            log.error(String.format(CRAWLER_ERROR.getLabel(), e.getMessage(), category.getUrl()));
                        }
                    }

                    article.setScrapDate(new Date());
                    article.setSiteId(category.getSiteId());
                    article.getLabelList().add(category.getLabel());
                    articleRepository.save(article);

                    // TODO news'body scrap

                    log.info(headLineDto.getUrl() + " added");
                } else if (!articleStored.getLabelList().contains(category.getLabel())) {
                    articleStored.getLabelList().add(category.getLabel());
                    articleRepository.save(articleStored);

                    log.info(headLineDto.getUrl() + " updated");
                } else {
                    break;
                }
            }

        } catch (WebyException e) {
            log.error(String.format(CRAWLER_ERROR.getLabel(), e.getMessage(), category.getUrl()));
        }
    }

}
