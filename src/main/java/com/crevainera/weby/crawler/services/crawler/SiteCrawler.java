package com.crevainera.weby.crawler.services.crawler;

import com.crevainera.weby.crawler.entities.Category;
import com.crevainera.weby.crawler.entities.Site;
import com.crevainera.weby.crawler.repositories.SiteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.Callable;

/**
 * Crawl configured sites and its categories
 */
@Service
@Slf4j
public class SiteCrawler {

    public static final String OK = "OK";

    private CategoryCrawlerExecutor categoryCrawlerExecutor;
    private SiteRepository siteRepository;
    private CategoryCrawler categoryCrawler;

    @Autowired
    public SiteCrawler(final CategoryCrawlerExecutor categoryCrawlerExecutor,
                       final SiteRepository siteRepository,
                       final CategoryCrawler categoryCrawler) {
        this.categoryCrawlerExecutor = categoryCrawlerExecutor;
        this.siteRepository = siteRepository;
        this.categoryCrawler = categoryCrawler;
    }

    public void crawlSites() {
        log.debug("crawling Sites");

        siteRepository.findByEnabledTrue().ifPresent(sites -> {
            sites.forEach(site -> {
                categoryCrawlerExecutor.addSiteCallables(callableCategoryCrawlerBySite(site));
            });
        });

        categoryCrawlerExecutor.executeAllEquitablyPerSite();
    }

    private Stack<Callable<String>> callableCategoryCrawlerBySite(final Site site) {
        Stack<Callable<String>> callableList = new Stack<>();

        Optional.ofNullable(site.getCategoryList()).ifPresent(categories -> {
            categories.stream().filter(Category::getEnabled).forEach(category -> {

                Callable<String> callable = () -> {
                    log.debug("crawling site:  " + site.getTitle() + ", category: " + category.getTitle());
                    categoryCrawler.crawlCategory(site, category);

                    return OK;
                };
                callableList.push(callable);
            });
        });

        return callableList;
    }
}
