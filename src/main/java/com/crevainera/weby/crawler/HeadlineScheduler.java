package com.crevainera.weby.crawler;

import com.crevainera.weby.crawler.services.headline.HeadlineServiceSitePool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class HeadlineScheduler {

    private HeadlineServiceSitePool siteCrawlerService;

    @Autowired
    public HeadlineScheduler(HeadlineServiceSitePool siteCrawlerService) {
        this.siteCrawlerService = siteCrawlerService;
    }

    @PostConstruct
    public void onStartup() {
        siteCrawlerService.executeParallelCrawlersBySite();
    }

    @Scheduled(cron = "${crawler.cron.expression}")
    public void executePeriodically() {
        log.info("executePeriodically");
        siteCrawlerService.executeParallelCrawlersBySite();
    }
}
