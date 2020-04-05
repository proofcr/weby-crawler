package com.crevainera.weby.crawler;

import com.crevainera.weby.crawler.services.SiteCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ScheduledTasks {

    private SiteCrawlerService siteCrawlerService;

    @Autowired
    public ScheduledTasks(SiteCrawlerService siteCrawlerService) {
        this.siteCrawlerService = siteCrawlerService;
    }

    @PostConstruct
    public void onStartup() {
        siteCrawlerService.executeParallelCrawlersBySite();
    }

    @Scheduled(cron = "${crawler.cron.expression}")
    public void executePeriodically() {
         siteCrawlerService.executeParallelCrawlersBySite();
    }
}
