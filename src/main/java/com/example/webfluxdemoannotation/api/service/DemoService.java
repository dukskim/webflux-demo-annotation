package com.example.webfluxdemoannotation.api.service;

import com.example.webfluxdemoannotation.api.dto.request.DemoDataRequest;
import com.example.webfluxdemoannotation.api.dto.response.DemoDataListResponse;
import com.example.webfluxdemoannotation.api.dto.response.DemoDataResponse;
import com.example.webfluxdemoannotation.entity.DemoTest;
import com.example.webfluxdemoannotation.repository.DemoTestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
     *  - DatabaseClient 사용
     *  - DemoTestCustomRepository, DemoTestCustomRepositoryImpl 만들고 DemoTestRepository 에 상속
     * @param demoDataRequest
     * @return
     */
    public Mono<DemoDataResponse> demoDbDataCustomQuery(DemoDataRequest demoDataRequest) {
        return demoTestRepository.findCustomByAaa(demoDataRequest.getAaa())
                .map(demoTest -> DemoDataResponse.builder().aaa(demoTest.getAaa()).bbb(demoTest.getBbb()).build())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "DemoTest not found")));
    }

    /**
     * DB 조회 예제 - SQL 복잡도 상 일떄 사용 
     *  - DatabaseClient 사용
     *  - DemoTestCustomRepository, DemoTestCustomRepositoryImpl 만들고 DemoTestRepository 에 상속
     *  - 일반적 동적 쿼리 사용
     * @param aaa
     * @param bbb
     * @return
     */
    public Mono<DemoDataListResponse> demoDbDataCustomQuery2(String aaa, String bbb) {
        /*
        자바의 스트림에서는 .map()이 각 항목을 처리합니다.
        WebFlux에서는 **Flux.map()**도 각 항목을 처리하는 방식이지만, 비동기 스트림이기 때문에 .collectList()를 사용하여 Flux의 항목들을 리스트로 모은 뒤 처리할 수 있습니다.
        Flux.map()은 각 항목을 처리하지만, collectList() 이후의 .map()은 전체 데이터를 한 번에 처리하는 방식이 됩니다.
         */
        return demoTestRepository.findCustomByCondition(aaa, bbb)
                .map(data -> DemoDataResponse.builder().aaa(data.getAaa()).bbb(data.getBbb()).build())
                .collectList()
                .map(data -> DemoDataListResponse.builder().dataList(data).build())
                ;
    }
}
