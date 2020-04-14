package com.crevainera.weby.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseErrorDTO {
    private String error;
    private String message;
    private String status;
    private String path;
}
