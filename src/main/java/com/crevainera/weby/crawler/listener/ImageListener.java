package com.crevainera.weby.crawler.listener;

import com.crevainera.weby.crawler.services.image.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.crevainera.weby.crawler.config.ActiveMQConfiguration.ARTICLE_ID_MESSAGE_QUEUE;

@Component
@Slf4j
public class ImageListener {

    private ImageService imageService;

    public ImageListener(final ImageService imageService) {
        this.imageService = imageService;
    }

    @JmsListener(destination = ARTICLE_ID_MESSAGE_QUEUE, containerFactory = "jmsFactory")
    public void receiveMessage(final String articleId) {
        log.info("Message received article.id(" + articleId + ")");
        imageService.process(Long.valueOf(articleId));
        log.info("Message processed article.id(" + articleId + ")");
    }
}