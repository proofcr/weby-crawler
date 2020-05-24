package com.crevainera.weby.crawler.services.headline;

import com.crevainera.weby.crawler.entities.Site;
import com.crevainera.weby.crawler.repositories.SiteRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class HeadlineServiceSitePool {

    private SiteRepository siteRepository;
    private HeadlineService crawlerService;
    private ExecutorService headLinesBySitePoolSize;

    @Autowired
    public HeadlineServiceSitePool(final ExecutorService headLinesBySitePoolSize,
                                   final SiteRepository siteRepository,
                                   final HeadlineService crawlerService) {
        this.headLinesBySitePoolSize = headLinesBySitePoolSize;
        this.siteRepository = siteRepository;
        this.crawlerService = crawlerService;
    }

    public void executeParallelCrawlersBySite() {
        log.info("executeParallelCrawlersBySite");

        List<Site> sites =  Lists.newArrayList(siteRepository.findByEnabledTrue());

        for (Site site : sites) {
            headLinesBySitePoolSize.submit(() -> crawlerService.crawlScrapAndSave(site));
        }
    }

}
