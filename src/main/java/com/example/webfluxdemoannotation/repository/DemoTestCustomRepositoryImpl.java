package com.example.webfluxdemoannotation.repository;

import com.example.webfluxdemoannotation.entity.DemoTest;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public Flux<DemoTest> findCustomByCondition(String aaa, String bbb) {
        StringBuilder query = new StringBuilder("SELECT aaa, bbb FROM demo_test WHERE 1=1 ");

        Map<String, Object> params = new HashMap<>();

        if (aaa != null) {
            query.append("AND aaa = :aaa ");
            params.put("aaa", aaa);
        }
        if (bbb != null) {
            query.append("AND bbb = :bbb ");
            params.put("bbb", bbb);
        }

        DatabaseClient.GenericExecuteSpec spec = databaseClient.sql(query.toString());

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            spec = spec.bind(entry.getKey(), entry.getValue());
        }

        return spec.map((row, metadata) ->
                DemoTest.builder()
                        .aaa(row.get("aaa", String.class))
                        .bbb(row.get("bbb", String.class))
                        .build()
        ).all();
    }
}
