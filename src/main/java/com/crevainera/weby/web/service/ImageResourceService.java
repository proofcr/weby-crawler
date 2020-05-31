package com.crevainera.weby.web.service;

import com.crevainera.weby.crawler.constant.WebyConstant;
import com.crevainera.weby.crawler.exception.WebyException;
import com.google.common.io.ByteStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.crevainera.weby.web.constant.ErrorMessages.SERVICE_DEFAULT_IMAGE_NOT_FOUND;

@Service
@Slf4j
public class ImageResourceService {

    private Resource resourceFile;

    public ImageResourceService(@Value("classpath:images/news.png") final Resource resourceFile) {
        this.resourceFile = resourceFile;
    }

    public byte[] getDefaultNewsImage() throws WebyException {
        log.debug("getDefaultNewsImage");
        try {
            return ByteStreams.toByteArray(resourceFile.getInputStream());
        } catch (IOException e) {
            log.error(SERVICE_DEFAULT_IMAGE_NOT_FOUND.getMessage(), e.getMessage());
            throw new WebyException(WebyConstant.ARTICLE_SERVICE_EXCEPTION.getMessage());
        }
    }
}
