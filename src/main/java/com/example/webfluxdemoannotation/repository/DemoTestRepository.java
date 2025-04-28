package com.example.webfluxdemoannotation.repository;

import com.example.webfluxdemoannotation.entity.DemoTest;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DemoTestRepository extends R2dbcRepository<DemoTest, String>, DemoTestCustomRepository {

    Mono<DemoTest> findByAaa(String aaa);

    @Query("SELECT aaa, bbb FROM demo_test WHERE aaa = :aaa")
    Mono<DemoTest> findQueryByAaa(String aaa);
}
