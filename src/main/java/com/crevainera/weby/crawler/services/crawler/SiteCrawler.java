package com.crevainera.weby.crawler.services.crawler;

import com.crevainera.weby.crawler.entities.Category;
import com.crevainera.weby.crawler.entities.Site;
import com.crevainera.weby.crawler.repositories.SiteRepository;
import com.crevainera.weby.crawler.util.CategoryIntermixer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

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
        log.debug("crawling Sites");

        List<Site> siteList = siteRepository.findByEnabledTrue();

        List<Category> categoryList = siteList.stream().map(s -> s.getCategoryList())
                .flatMap(List::stream).collect(Collectors.toList());

        CategoryIntermixer.getCategoriesIntermingledPerSite(categoryList).forEach(category -> {
                Site site = siteList.stream().filter(s -> s.getId() == category.getSiteId()).findAny().get();
                headLinesBySitePoolSize.submit(callableCategoryCrawler(site, category));
            });
    }

    private Callable<String> callableCategoryCrawler(final Site site, final Category category) {

        Callable<String> callable = () -> {
            log.debug("crawling site:  " + site.getTitle() + ", category: " + category.getTitle());
            categoryCrawler.crawlCategory(site, category);

            return OK;
        };

        return callable;
    }
}
