package com.example.webfluxdemoannotation.repository;

import com.example.webfluxdemoannotation.entity.DemoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DemoTestCustomRepository {

    Mono<DemoTest> findCustomByAaa(String aaa);

    Flux<DemoTest> findCustomByCondition(String aaa, String bbb);
}
