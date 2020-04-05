package com.crevainera.weby.crawler.services;

import com.crevainera.weby.crawler.constant.WebyConstant;
import com.crevainera.weby.crawler.exception.WebyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;


@Service
@Slf4j
public class ImageService {

    public byte[] resize(final URL url, final Integer maxWidth, final Integer maxHeight) throws WebyException {
        try {
            BufferedImage img = ImageIO.read(url);

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

            String formatName = getImageExtensionFromBytes(url) ;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(buffered, formatName, out);

            return out.toByteArray();

        } catch (IOException ioException) {
            log.error(WebyConstant.IMAGE_SERVICE_IMAGE_NAME.name(), ioException.getStackTrace().toString());
            throw new WebyException(WebyConstant.IMAGE_SERVICE_IMAGE_NAME.name());
        }
    }

    private String getImageExtensionFromBytes(final URL url) throws IOException {
        InputStream is = url.openStream();
        ImageInputStream iis = ImageIO.createImageInputStream(is);
        Iterator iterator = ImageIO.getImageReaders(iis);
        if (!iterator.hasNext()) {
            return null;
        }
        ImageReader reader = (ImageReader) iterator.next();
        is.close();
        iis.close();

        return reader.getFormatName();
    }
}
