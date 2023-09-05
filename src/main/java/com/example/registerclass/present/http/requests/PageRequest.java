package com.example.registerclass.present.http.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PageRequest {
    @Min(0)
    Integer page;
    @Min(0)
    Integer size;


    public PageRequest() {
        page = 1;
        size = 10;
    }

    public Integer getPage() {
        return page - 1;
    }
}
