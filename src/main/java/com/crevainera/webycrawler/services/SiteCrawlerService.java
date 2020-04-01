package com.crevainera.webycrawler.services;


import com.crevainera.webycrawler.entities.Site;
import com.crevainera.webycrawler.repositories.SiteRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SiteCrawlerService {

    private SiteRepository siteRepository;
    private CategoryCrawlerService crawlerService;
    private ExecutorService poolForSites;

    @Autowired
    public SiteCrawlerService(final ExecutorService poolForSites,
                              final SiteRepository siteRepository,
                              final CategoryCrawlerService crawlerService) {
        this.poolForSites = poolForSites;
        this.siteRepository = siteRepository;
        this.crawlerService = crawlerService;
    }

    public void  executeParallelCrawlersBySite() {
        List<Site> sites =  Lists.newArrayList(siteRepository.findAll());
        for (Site site : sites) {
            poolForSites.submit(() -> crawlerService.crawlScrapAndSave(site));
        }
    }

}
