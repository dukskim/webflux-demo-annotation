package com.example.webfluxdemoannotation.repository;

import com.example.webfluxdemoannotation.entity.DemoTest;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class DemoTestCustomRepositoryImpl implements DemoTestCustomRepository {

    private final DatabaseClient databaseClient;

    @Override
    public Mono<DemoTest> findCustomByAaa(String aaa) {
        return databaseClient.sql("SELECT aaa, bbb FROM demo_test WHERE aaa = :aaa")
                .bind("aaa", aaa)
                .map((row, rowMetadata) -> DemoTest.builder()
                        .aaa(row.get("aaa", String.class))
                        .bbb(row.get("bbb", String.class))
                        .build())
                .one();
    }
}
