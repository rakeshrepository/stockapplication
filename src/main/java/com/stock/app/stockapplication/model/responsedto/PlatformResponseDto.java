package com.stock.app.stockapplication.model.responsedto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlatformResponseDto<T> {

    @JsonProperty("response")
    private T resPayload;

    /**
     * This constructor is used for successful response from the platform
     *
     * @param resPayload
     */
    public PlatformResponseDto(T resPayload) {
        this.resPayload = resPayload;
    }
}
