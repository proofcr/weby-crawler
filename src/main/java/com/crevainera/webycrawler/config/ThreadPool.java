package com.crevainera.webycrawler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPool {

    private Integer parallelSites;

    public ThreadPool(@Value("${crawler.parallelSites}") final Integer parallelSites) {
        this.parallelSites = parallelSites;
    }

    @Bean(name="poolForSites")
    public ExecutorService poolForSites() {
        return Executors.newFixedThreadPool(parallelSites);
    }
}
