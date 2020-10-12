package com.crevainera.weby.crawler.services.crawler;

import com.crevainera.weby.crawler.entities.Category;
import com.crevainera.weby.crawler.entities.Site;
import com.crevainera.weby.crawler.repositories.SiteRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

/**
 * Crawl configured sites and its categories
 */
@Service
@Slf4j
public class SiteCrawler {

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

        siteRepository.findByEnabledTrue().forEach(site -> {
                headLinesBySitePoolSize.submit(() -> crawlSiteCategories(site));
            });
    }

    public void crawlSiteCategories(final Site site) {
        log.debug("thread for site: " + site.getUrl() + " (" + site.getTitle()+ ")");

        site.getCategoryList().stream().filter(Category::getEnabled).forEach(category -> {
                    categoryCrawler.crawlCategory(site, category);
                });
    }
}
