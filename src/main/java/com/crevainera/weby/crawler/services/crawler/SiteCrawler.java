package com.crevainera.weby.crawler.services.crawler;

import com.crevainera.weby.crawler.entities.Category;
import com.crevainera.weby.crawler.entities.Site;
import com.crevainera.weby.crawler.repositories.SiteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * Crawl configured sites and its categories
 */
@Service
@Slf4j
public class SiteCrawler {

    public static final String OK = "OK";

    private SitePool sitePool;
    private SiteRepository siteRepository;
    private CategoryCrawler categoryCrawler;

    @Autowired
    public SiteCrawler(final SitePool sitePool,
                       final SiteRepository siteRepository,
                       final CategoryCrawler categoryCrawler) {
        this.sitePool = sitePool;
        this.siteRepository = siteRepository;
        this.categoryCrawler = categoryCrawler;
    }

    public void crawlSites() {
        log.debug("crawling Sites");

        siteRepository.findByEnabledTrue().ifPresent(sites -> {
            sites.forEach(site -> {
                sitePool.addSiteCallables(callableTaskBySite(site));
            });
        });

        sitePool.submitAllEquitablyPerSite();
    }

    private Stack<Callable<String>> callableTaskBySite(final Site site) {
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
