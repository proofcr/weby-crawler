package com.crevainera.weby.crawler.services.image;

import com.crevainera.weby.crawler.exception.WebyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class ThumbService {

    private Integer maxWidth;
    private Integer maxHeight;
    private ImageProcessorService imageProcessorService;

    @Autowired
    public ThumbService(final ImageProcessorService imageProcessorService,
                        @Value("${scraper.thumb.maxWidth}") final Integer maxWidth,
                        @Value("${scraper.thumb.maxWidth}") final Integer maxHeight) {
        this.imageProcessorService = imageProcessorService;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    public byte[] resize(final URL url) throws WebyException {
        return imageProcessorService.resize(url, maxWidth, maxHeight);
    }
}
