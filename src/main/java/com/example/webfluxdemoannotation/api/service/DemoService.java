package com.example.webfluxdemoannotation.api.service;

import com.example.webfluxdemoannotation.api.dto.request.DemoDataRequest;
import com.example.webfluxdemoannotation.api.dto.response.DemoDataResponse;
import com.example.webfluxdemoannotation.repository.DemoTestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class DemoService {

    private final DemoTestRepository demoTestRepository;

    public Mono<DemoDataResponse> findDemoData() {
        return Mono.just(DemoDataResponse.builder().aaa("a").bbb("b").build());
    }

    public Mono<DemoDataResponse> demoDbData(DemoDataRequest demoDataRequest) {
        return demoTestRepository.findByAaa(demoDataRequest.getAaa())
                .map(demoTest -> DemoDataResponse.builder().aaa(demoTest.getAaa()).bbb(demoTest.getBbb()).build())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "DemoTest not found")));
    }

}
