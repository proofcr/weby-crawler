package com.crevainera.weby.crawler;

import com.crevainera.weby.crawler.services.crawler.SiteCrawler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class CrawlerScheduler {

    private SiteCrawler crawlerSitePool;

    @Autowired
    public CrawlerScheduler(SiteCrawler crawlerSitePool) {
        this.crawlerSitePool = crawlerSitePool;
    }

    @PostConstruct
    public void onStartup() {
        crawlerSitePool.crawlSites();
    }


    @Scheduled(cron = "${crawler.cron.expression}")
    public void executePeriodically() {
        log.info("executePeriodically");
        crawlerSitePool.crawlSites();
    }
}
