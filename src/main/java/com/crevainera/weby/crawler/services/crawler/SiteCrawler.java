package com.crevainera.weby.crawler.services.crawler;

import com.crevainera.weby.crawler.entities.Category;
import com.crevainera.weby.crawler.entities.Site;
import com.crevainera.weby.crawler.repositories.SiteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * Crawl configured sites and its categories
 */
@Service
@Slf4j
public class SiteCrawler {

    public static final String OK = "OK";
    private SiteRepository siteRepository;
    private ExecutorService headLinesBySitePoolSize;
    private CategoryCrawler categoryCrawler;

    @Autowired
    public SiteCrawler(final SiteRepository siteRepository,
                       final ExecutorService headLinesBySitePoolSize,
                       final CategoryCrawler categoryCrawler) {
        this.siteRepository = siteRepository;
        this.headLinesBySitePoolSize = headLinesBySitePoolSize;
        this.categoryCrawler = categoryCrawler;
    }

    public void crawlSites() {
        log.debug("executeParallelCrawlersBySite");
        siteRepository.findByEnabledTrue().ifPresent(sites -> {
                sites.forEach(site -> {
                    try {
                        headLinesBySitePoolSize.invokeAll(callableTaskBySite(site));
                    } catch (InterruptedException e) {
                        log.error("Site's thread pool error");
                    }
                });
            });
        log.debug("all task finished");
    }

    private List<Callable<String>> callableTaskBySite(final Site site) {
        List<Callable<String>> callableList = new ArrayList<>();

        Optional.ofNullable(site.getCategoryList()).ifPresent(categories -> {
            categories.stream().filter(Category::getEnabled).forEach(category -> {

                Callable<String> callable = () -> {
                    log.debug("crawling site: " + site.getUrl() + " (" + site.getTitle() + ")");
                    categoryCrawler.crawlCategory(site, category);

                    return OK;
                };
                callableList.add(callable);
            });
        });

        return callableList;
    }
}
