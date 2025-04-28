package com.example.webfluxdemoannotation.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoDataListResponse {
    private List<DemoDataResponse> dataList;
}
