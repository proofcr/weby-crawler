package com.crevainera.weby.web.constant;

import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.crevainera.weby.web.constant.ImageFormatEnum.*;

public class ImageFormatMediaTypeMap {

    private static final Map<String, MediaType> imageFormatMediaTypeMap = createMap();

    private static Map<String, MediaType> createMap() {
        Map<String, MediaType> imageFormatMediaTypeMap = new HashMap<>();

        imageFormatMediaTypeMap.put(JPG.getName(), MediaType.IMAGE_JPEG);
        imageFormatMediaTypeMap.put(JPEG.getName(), MediaType.IMAGE_JPEG);
        imageFormatMediaTypeMap.put(PNG.getName(), MediaType.IMAGE_PNG);
        imageFormatMediaTypeMap.put(GIF.getName(), MediaType.IMAGE_GIF);

        return Collections.unmodifiableMap(imageFormatMediaTypeMap);
    }

    public static MediaType get(final String formatKey) {
        return imageFormatMediaTypeMap.getOrDefault(formatKey.toLowerCase(), MediaType.IMAGE_JPEG);
    }

}
