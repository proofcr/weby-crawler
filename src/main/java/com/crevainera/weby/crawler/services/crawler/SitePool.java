package com.crevainera.weby.crawler.services.crawler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

@Component
@Slf4j
public class SitePool {

    private ExecutorService headLinesBySitePoolSize;

    private List<Stack<Callable<String>>> siteCallableList;
    private int nextIndex;

    @Autowired
    public SitePool(final ExecutorService headLinesBySitePoolSize) {
        this.headLinesBySitePoolSize = headLinesBySitePoolSize;

        log.debug("SitePool constructor injection");
    }

    @PostConstruct
    protected void initializePostConstruct() {
        log.debug("SitePool post construct initialized");
        siteCallableList = new ArrayList<>();
        this.nextIndex = 0;
    }

    public void addSiteCallables(Stack<Callable<String>> siteCallables) {
        siteCallableList.add(siteCallables);

    }

    public void submitAllEquitablyPerSite() {
        log.debug("SitePool submitting");
        while (isPoolEmpty()) {
            if (!isEmptyPoolPerCurrentSite()) {
                headLinesBySitePoolSize.submit(getCurrentCallableStack().pop());
            }
            nextIndex();
        }
        log.debug("SitePool submitted");
    }

    private int nextIndex() {
        if (nextIndex < siteCallableList.size()-1) {
            nextIndex++;
        } else {
            nextIndex = 0;
        }

        return nextIndex;
    }

    private boolean isEmptyPoolPerCurrentSite() {
        return siteCallableList.get(getCurrentIndex()).empty();
    }

    private Stack<Callable<String>> getCurrentCallableStack() {
        return siteCallableList.get(getCurrentIndex());
    }

    private int getCurrentIndex() {
        return nextIndex;
    }

    private boolean isPoolEmpty() {
        boolean isEmptyList = false;
        for (int i=0 ; i < this.siteCallableList.size(); i++) {
            if (!this.siteCallableList.get(i).empty()) {
                isEmptyList = true;
            }
        }
        return isEmptyList;
    }
}