package com.crevainera.weby.crawler.services.image;

import com.crevainera.weby.crawler.constant.WebyConstant;
import com.crevainera.weby.crawler.exception.WebyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


@Service
@Slf4j
public class ImageProcessorService {
    public static final String USER_AGENT = "User-Agent";
    private String browserName;

    public ImageProcessorService(@Value("${crawler.browser}") final String browserName) {
        log.info("Crawler's browser: " + browserName);
        this.browserName = browserName;
    }

    public byte[] resize(final URL url, final Integer maxWidth, final Integer maxHeight) throws WebyException {
        try {

            InputStream inputStream = getUrlConnection(url).getInputStream();
            BufferedImage img = ImageIO.read(inputStream);

            int scaledWidth = 0, scaledHeight = 0;

            scaledWidth = maxWidth;
            scaledHeight = (int) (img.getHeight() * ((double) scaledWidth / img.getWidth()));

            if (scaledHeight> maxHeight) {
                scaledHeight = maxHeight;
                scaledWidth= (int) (img.getWidth() * ((double) scaledHeight/ img.getHeight()));

                if (scaledWidth > maxWidth) {
                    scaledWidth = maxWidth;
                    scaledHeight = maxHeight;
                }
            }

            Image resized =  img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

            BufferedImage buffered = new BufferedImage(scaledWidth, scaledHeight, Image.SCALE_REPLICATE);
            buffered.getGraphics().drawImage(resized, 0, 0 , null);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(buffered, FilenameUtils.getExtension(url.toString()), out);

            return out.toByteArray();

        } catch (IOException ioException) {
            log.error(WebyConstant.IMAGE_SERVICE_IMAGE_NAME.getMessage(), ioException.getStackTrace().toString());
            throw new WebyException(WebyConstant.IMAGE_SERVICE_IMAGE_NAME.getMessage());
        }
    }

    private URLConnection getUrlConnection(final URL url) throws WebyException {
        try {
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty(USER_AGENT, browserName);

            return urlConnection;
        } catch (IOException e) {
            throw new WebyException(WebyConstant.NOT_FOUND_URL.getMessage());
        }
     }
}
