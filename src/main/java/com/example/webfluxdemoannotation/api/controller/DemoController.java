package com.example.webfluxdemoannotation.api.controller;

import com.example.webfluxdemoannotation.api.dto.response.DemoDataResponse;
import com.example.webfluxdemoannotation.api.service.DemoService;
import com.example.webfluxdemoannotation.base.dto.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class DemoController {

    private final DemoService demoService;

    @GetMapping("/api/v1/demo/data")
    public Mono<ResponseEntity<SingleResponse<DemoDataResponse>>> demoData() {
        return demoService.findDemoData()
                .map(data -> ResponseEntity.ok(new SingleResponse<>(data)));
    }
}
