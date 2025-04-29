package com.example.webfluxdemoannotation.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemoValidTestRequest {

    @NotBlank(message = "A2001:aaa")
    private String aaa;

    @NotBlank(message = "A2002:bbb,3")
    @Size(min = 3, message = "A2002:bbb,3")
    private String bbb;
}
