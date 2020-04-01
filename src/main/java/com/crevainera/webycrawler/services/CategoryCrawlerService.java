package com.crevainera.webycrawler.services;

import com.crevainera.webycrawler.dto.HeadLineDto;
import com.crevainera.webycrawler.entities.Article;
import com.crevainera.webycrawler.entities.Category;
import com.crevainera.webycrawler.entities.ScrapRule;
import com.crevainera.webycrawler.entities.Site;
import com.crevainera.webycrawler.exception.WebyException;
import com.crevainera.webycrawler.repositories.ArticleRepository;
import com.crevainera.webycrawler.repositories.LabelRepository;
import com.crevainera.webycrawler.repositories.ScrapRuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.crevainera.webycrawler.constant.WebyConstant.CRAWLER_ERROR;

@Service
@Slf4j
public class CategoryCrawlerService {

    public static final String BAR_CHARACTER = "/";
    public static final String HTTP_PROTOCOL = "http";

    private CategoryScraperService scrapService;
    private HtmlDocumentService documentFromHtml;
    private ArticleRepository articleRepository;

    @Autowired
    public CategoryCrawlerService(final CategoryScraperService scrapService,
                                  final HtmlDocumentService documentFromHtml,
                                  final ArticleRepository articleRepository) {
        this.scrapService = scrapService;
        this.documentFromHtml = documentFromHtml;
        this.articleRepository = articleRepository;
    }

    public void crawlScrapAndSave(final Site site) {
        log.info("crawlScrapAndSave site: " + site.getUrl() + " (" + site.getTitle()+ ")");

        site.getCategoryList().forEach(category -> crawlScrapAndSave(site, category));
    }

    private void crawlScrapAndSave(final Site site, final Category category) {
        ScrapRule scrapRule = category.getScrapRule();
         try {
            Document document = documentFromHtml.getDocument(category.getUrl());

            for (HeadLineDto headLineDto : scrapService.scrap(document, scrapRule)) {

                Article articleStored = articleRepository.findByUrl(headLineDto.getUrl());

                if (articleStored == null) {
                    Article article = new Article();
                    article.setTitle(headLineDto.getTitle());
                    article.setUrl(headLineDto.getUrl());
                    if (headLineDto.getThumbUrl().startsWith(HTTP_PROTOCOL)) {
                        article.setThumbUrl(headLineDto.getThumbUrl());
                    } else {
                        article.setThumbUrl(site.getUrl() + BAR_CHARACTER + headLineDto.getThumbUrl());
                    }
                    article.setScrapDate(new Date());
                    article.setSiteId(category.getSiteId());
                    article.getLabelList().add(category.getLabel());
                    articleRepository.save(article);

                    // TODO transform image url into a thumb as save as binary in Article table
                    // TODO content scrap

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
            log.error(String.format(CRAWLER_ERROR.name(), e.getMessage(),  category.getUrl()));
        }
    }

}
