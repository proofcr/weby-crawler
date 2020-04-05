package com.crevainera.webycrawler.services;

import com.crevainera.webycrawler.exception.WebyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class ThumbService {

    private Integer maxWidth;
    private Integer maxHeight;
    private ImageService imageService;

    @Autowired
    public ThumbService(final ImageService imageService,
                        @Value("${scraper.thumb.maxWidth}") final Integer maxWidth,
                        @Value("${scraper.thumb.maxWidth}") final Integer maxHeight) {
        this.imageService = imageService;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    public byte[] resize(final URL url) throws WebyException {
        return imageService.resize(url, maxWidth, maxHeight);
    }
}
