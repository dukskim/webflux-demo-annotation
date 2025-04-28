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

    /**
     * 단순 데이터 응답 예제
     * @return
     */
    public Mono<DemoDataResponse> findDemoData() {
        return Mono.just(DemoDataResponse.builder().aaa("a").bbb("b").build());
    }

    /**
     * DB 조회 예제
     * @param demoDataRequest
     * @return
     */
    public Mono<DemoDataResponse> demoDbData(DemoDataRequest demoDataRequest) {
        return demoTestRepository.findByAaa(demoDataRequest.getAaa())
                .map(demoTest -> DemoDataResponse.builder().aaa(demoTest.getAaa()).bbb(demoTest.getBbb()).build())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "DemoTest not found")));
    }

    /**
     * DB 조회 예제 - SQL 복잡도 중 일떄 사용
     *  - R2dbcRepository 에서 @Query 어노테이션 사용
     * @param demoDataRequest
     * @return
     */
    public Mono<DemoDataResponse> demoDbDataQuery(DemoDataRequest demoDataRequest) {
        return demoTestRepository.findQueryByAaa(demoDataRequest.getAaa())
                .map(demoTest -> DemoDataResponse.builder().aaa(demoTest.getAaa()).bbb(demoTest.getBbb()).build())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "DemoTest not found")));
    }

    /**
     * DB 조회 예제 - SQL 복잡도 상 일떄 사용
     *  - DemoTestCustomRepository, DemoTestCustomRepositoryImpl 만들고 DemoTestRepository 에 상속
     * @param demoDataRequest
     * @return
     */
    public Mono<DemoDataResponse> demoDbDataCustomQuery(DemoDataRequest demoDataRequest) {
        return demoTestRepository.findCustomByAaa(demoDataRequest.getAaa())
                .map(demoTest -> DemoDataResponse.builder().aaa(demoTest.getAaa()).bbb(demoTest.getBbb()).build())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "DemoTest not found")));
    }
}
