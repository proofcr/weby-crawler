package com.crevainera.weby.crawler.services.headline;


import com.crevainera.weby.crawler.entities.Site;
import com.crevainera.weby.crawler.repositories.SiteRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
public class HeadlineServiceSitePool {

    private SiteRepository siteRepository;
    private HeadlineCrawlerService crawlerService;
    private ExecutorService headLinesBySitePoolSize;

    @Autowired
    public HeadlineServiceSitePool(final ExecutorService headLinesBySitePoolSize,
                                   final SiteRepository siteRepository,
                                   final HeadlineCrawlerService crawlerService) {
        this.headLinesBySitePoolSize = headLinesBySitePoolSize;
        this.siteRepository = siteRepository;
        this.crawlerService = crawlerService;
    }

    public void  executeParallelCrawlersBySite() {
        List<Site> sites =  Lists.newArrayList(siteRepository.findByEnabledTrue());

        for (Site site : sites) {
            headLinesBySitePoolSize.submit(() -> crawlerService.crawlScrapAndSave(site));
        }
    }

}
