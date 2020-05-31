package com.crevainera.weby.web.service;

import com.google.common.io.ByteStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.crevainera.weby.web.error.ErrorMessages.SERVICE_DEFAULT_IMAGE_NOT_FOUND;

@Service
@Slf4j
public class ImageResourceService {

    @Value("classpath:images/news.png")
    private Resource resourceFile;

    public byte[] getDefaultNewsImage() {
        log.debug("getDefaultNewsImage");
        try {
            return ByteStreams.toByteArray(resourceFile.getInputStream());
        } catch (IOException e) {
            log.error(SERVICE_DEFAULT_IMAGE_NOT_FOUND.getMessage(), e.getMessage());
        }

        return null;
    }
}
