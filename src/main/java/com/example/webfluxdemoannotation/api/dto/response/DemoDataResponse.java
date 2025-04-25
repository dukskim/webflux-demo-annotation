package com.example.webfluxdemoannotation.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoDataResponse {
    private String aaa;
    private String bbb;
}
