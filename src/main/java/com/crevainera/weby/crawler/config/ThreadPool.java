package com.crevainera.weby.crawler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPool {

    private Integer headLinesBySitePool;


    public ThreadPool(@Value("${crawler.headLinesBySitePool}") final Integer headLinesBySitePool) {
        this.headLinesBySitePool = headLinesBySitePool;
    }

    @Bean(name="headLinesBySitePool")
    public ExecutorService getHeadLinesBySitePool() {
        return Executors.newFixedThreadPool(headLinesBySitePool);
    }

 }
