package com.example.webfluxdemoannotation.api.service;

import com.example.webfluxdemoannotation.api.dto.response.DemoDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class DemoService {

    public Mono<DemoDataResponse> findDemoData() {
        return Mono.just(DemoDataResponse.builder().aaa("a").bbb("b").build());
    }
}
