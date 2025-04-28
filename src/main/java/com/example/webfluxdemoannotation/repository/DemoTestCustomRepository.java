package com.example.webfluxdemoannotation.repository;

import com.example.webfluxdemoannotation.entity.DemoTest;
import reactor.core.publisher.Mono;

public interface DemoTestCustomRepository {

    Mono<DemoTest> findCustomByAaa(String aaa);
}
