package com.crevainera.webycrawler.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;


@Service
@Slf4j
public class ImageService {

    public static byte[] resize(File file, int maxWidth, int maxHeight) throws IOException {
        int scaledWidth = 0, scaledHeight = 0;

        BufferedImage img = ImageIO.read(file);

        scaledWidth = maxWidth;
        scaledHeight = (int) (img.getHeight() * ( (double) scaledWidth / img.getWidth() ));

        if (scaledHeight> maxHeight) {
            scaledHeight = maxHeight;
            scaledWidth= (int) (img.getWidth() * ( (double) scaledHeight/ img.getHeight() ));

            if (scaledWidth > maxWidth) {
                scaledWidth = maxWidth;
                scaledHeight = maxHeight;
            }
        }

        Image resized =  img.getScaledInstance( scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

        BufferedImage buffered = new BufferedImage(scaledWidth, scaledHeight, Image.SCALE_REPLICATE);
        buffered.getGraphics().drawImage(resized, 0, 0 , null);

        String formatName = getFormatName(file) ;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(buffered, formatName, out);
        return out.toByteArray();
    }

    private static String getFormatName(final ImageInputStream iis) {
        try {
            Iterator iterator = ImageIO.getImageReaders(iis);
            if (!iterator.hasNext()) {
                return null;
            }
            ImageReader reader = (ImageReader) iterator.next();
            iis.close();
            return reader.getFormatName();
        } catch (IOException e) {
            log.error(e.getStackTrace().toString());
        }
        return null;
    }

    private static String getFormatName(final File file) throws IOException {
        return getFormatName(ImageIO.createImageInputStream(file));
    }

    private static String getFormatName(final InputStream inputStream) throws IOException {
        return getFormatName(ImageIO.createImageInputStream(inputStream));
    }
}
