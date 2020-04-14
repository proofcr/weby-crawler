package com.crevainera.weby.web.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {

    ENDPOINT_WE_ARE_WORKING("Internal Server Error. We are working to fix it."),
    ENDPOINT_VALIDATION_ERROR("Validation Error"),
    ENDPOINT_INTERNAL_SERVER_ERROR("Internal Server Error");

    private String message;
}
