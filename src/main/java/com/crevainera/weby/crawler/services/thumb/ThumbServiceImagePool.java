package com.crevainera.weby.crawler.services.thumb;

import com.crevainera.weby.crawler.entities.Article;
import com.crevainera.weby.crawler.entities.Site;
import com.crevainera.weby.crawler.exception.WebyException;
import com.crevainera.weby.crawler.repositories.ArticleRepository;
import com.crevainera.weby.crawler.repositories.SiteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@Slf4j
public class ThumbServiceImagePool {

    private Long thumbResizeLoopMillisecondDelay;
    private Integer articleIdForThumbQueueSize; // TODO evaluate queueSize impact
    private Map<Long, BlockingQueue<Long>> queueMap;

    private ArticleRepository articleRepository;
    private SiteRepository siteRepository;
    private ThumbService thumbService;

    @Autowired
    public ThumbServiceImagePool(
            @Value("${crawler.thumbResizeLoopMillisecondDelay:200}") final Long thumbResizeLoopMillisecondDelay,
            @Value("${crawler.articleIdForThumbQueueSize:100}") final Integer articleIdForThumbQueueSize,
            final SiteRepository siteRepository,
            final ArticleRepository articleRepository,
            final ThumbService thumbService) {
        this.thumbResizeLoopMillisecondDelay = thumbResizeLoopMillisecondDelay;
        this.articleIdForThumbQueueSize = articleIdForThumbQueueSize;
        this.siteRepository = siteRepository;
        this.articleRepository = articleRepository;
        this.thumbService = thumbService;
    }


    private void initQueues() {
        Iterable<Site> siteIterable = siteRepository.findAll();

        queueMap = new HashMap<>();
        Iterator<Site> iterator = siteIterable.iterator();
        while (iterator.hasNext())  {
            Site site = iterator.next();
            queueMap.put(site.getId(), new LinkedBlockingQueue<>(articleIdForThumbQueueSize));
        }
        log.info("initQueues");
    }

    public void put(final Article article) {
        BlockingQueue blockingQueue = queueMap.get(article.getSiteId()); // TODO evaluate wait until push impact
        try {
            blockingQueue.put(article.getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void executeThumbBySite() { // TODO improve

        queueMap.forEach((siteId, queue) -> {
                Runnable task = () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            Long articleId = queue.poll(); // TODO evaluate wait until poll impact
                            if (articleId != null) {
                                Article article = articleRepository.findById(articleId).get();
                                article.setThumb(thumbService.resize(new URL(article.getThumbUrl())));
                                articleRepository.save(article);

                            }
                            Thread.sleep(thumbResizeLoopMillisecondDelay);
                        } catch (WebyException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                new Thread(task).start();
            }
        );
        log.info("executeThumbBySite");
    }

    @PostConstruct
    public void onStartup() {
        initQueues();
        executeThumbBySite();
    }

}
