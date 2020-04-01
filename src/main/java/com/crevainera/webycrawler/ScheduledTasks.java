package com.crevainera.webycrawler;

import com.crevainera.webycrawler.services.SiteCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTasks {

    private SiteCrawlerService siteCrawlerService;

    @Autowired
    public ScheduledTasks(SiteCrawlerService siteCrawlerService) {
        this.siteCrawlerService = siteCrawlerService;
    }

    @Scheduled(cron = "${crawler.cron.expression}")
    public void executePeriodically() {
         siteCrawlerService.executeParallelCrawlersBySite();
    }
}
