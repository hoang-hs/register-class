package com.example.registerclass.present.http.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class PageRequest {
    @Min(0)
    @JsonProperty("page")
    Integer page;
    @Min(0)
    @JsonProperty("size")
    Integer size;


    public PageRequest() {
        page = 1;
        size = 10;
    }

    public Integer getPage() {
        return page - 1;
    }
}
