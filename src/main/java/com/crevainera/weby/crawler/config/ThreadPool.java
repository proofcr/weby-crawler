package com.crevainera.weby.crawler.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@Slf4j
public class ThreadPool {

    private Integer headLinesBySitePoolSize;

    public ThreadPool(@Value("${crawler.headLinesBySitePoolSize:4}") final Integer headLinesBySitePoolSize) {
        this.headLinesBySitePoolSize = headLinesBySitePoolSize;
    }

    @Bean(name="headLinesBySitePool")
    public ExecutorService getHeadLinesBySitePool() {

        return Executors.newFixedThreadPool(headLinesBySitePoolSize);
    }

 }
