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
                    callableTaskBySite(site).forEach(callable -> {
                        headLinesBySitePoolSize.submit(callable);
                    });
                });
            });
        log.debug("all task finished");
    }

    private List<Callable<String>> callableTaskBySite(final Site site) {
        List<Callable<String>> callableList = new ArrayList<>();

        Optional.ofNullable(site.getCategoryList()).ifPresent(categories -> {
            categories.stream().filter(Category::getEnabled).forEach(category -> {

                Callable<String> callable = () -> {
                    log.debug("crawling site:  " + site.getTitle() + ", category: " + category.getTitle());
                    categoryCrawler.crawlCategory(site, category);

                    return OK;
                };
                callableList.add(callable);
            });
        });

        return callableList;
    }
}
