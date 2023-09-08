package com.example.registerclass.present.http.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetRegistrationRequest {
    @NotNull
    @Min(1)
    Long studentId;

    @NotNull
    String status;

    PageRequest pageRequest;

    GetRegistrationRequest() {
        this.pageRequest = new PageRequest();
    }
}
