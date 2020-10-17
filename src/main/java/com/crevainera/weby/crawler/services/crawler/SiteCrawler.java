package com.crevainera.weby.crawler.services.crawler;

import com.crevainera.weby.crawler.entities.Category;
import com.crevainera.weby.crawler.entities.Site;
import com.crevainera.weby.crawler.repositories.SiteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * Crawl configured sites and its categories
 */
@Service
@Slf4j
public class SiteCrawler {

    public static final String OK = "OK";
    private SiteRepository siteRepository;
    private ExecutorService headLinesBySitePoolSize;
    private CategoryCrawler categoryCrawler;

    @Autowired
    public SiteCrawler(final SiteRepository siteRepository,
                       final ExecutorService headLinesBySitePoolSize,
                       final CategoryCrawler categoryCrawler) {
        this.siteRepository = siteRepository;
        this.headLinesBySitePoolSize = headLinesBySitePoolSize;
        this.categoryCrawler = categoryCrawler;
    }

    public void crawlSites() {
        log.debug("crawling Sites");
        SitePool sitePool = new SitePool();

        siteRepository.findByEnabledTrue().ifPresent(sites -> {
            sites.forEach(site -> {
                sitePool.addSiteCallables(callableTaskBySite(site));
            });
        });

        sitePool.submitAllEquitablyPerSite();
    }

    private Stack<Callable<String>> callableTaskBySite(final Site site) {
        Stack<Callable<String>> callableList = new Stack<>();

        Optional.ofNullable(site.getCategoryList()).ifPresent(categories -> {
            categories.stream().filter(Category::getEnabled).forEach(category -> {

                Callable<String> callable = () -> {
                    log.debug("crawling site:  " + site.getTitle() + ", category: " + category.getTitle());
                    categoryCrawler.crawlCategory(site, category);

                    return OK;
                };
                callableList.push(callable);
            });
        });

        return callableList;
    }

    private class SitePool {
        private List<Stack<Callable<String>>> siteCallableList;
        private int nextIndex;

        public SitePool() {
            siteCallableList = new ArrayList<>();
            this.nextIndex = 0;
            log.debug("SitePool initialized");
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
}
