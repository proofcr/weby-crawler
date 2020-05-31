package com.crevainera.weby.web.constant;

public enum ImageFormatEnum {

    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    GIF("gif");

    private String name;

    ImageFormatEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
