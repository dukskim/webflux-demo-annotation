package com.example.webfluxdemoannotation.api.controller;

import com.example.webfluxdemoannotation.api.dto.request.DemoDataRequest;
import com.example.webfluxdemoannotation.api.dto.request.DemoValidTestRequest;
import com.example.webfluxdemoannotation.api.dto.response.DemoDataListResponse;
import com.example.webfluxdemoannotation.api.dto.response.DemoDataResponse;
import com.example.webfluxdemoannotation.api.service.DemoService;
import com.example.webfluxdemoannotation.base.dto.ApiResult;
import com.example.webfluxdemoannotation.base.enums.ErrorCode;
import com.example.webfluxdemoannotation.core.exception.CustomException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Slf4j
@Validated  // Get Method 의 RequestParam 검증 시 사용 ->> @NotBlank(message = "A2001:aaa")@RequestParam(value = "aaa", required = false)String aaa,
@RestController
@RequiredArgsConstructor
public class DemoController {

    private final DemoService demoService;

    private final MessageSource messageSource;

    @GetMapping("/api/v1/demo/data")
    public Mono<ResponseEntity<ApiResult<DemoDataResponse>>> demoData(Locale locale) {
        String msg = messageSource.getMessage("A0001", null, locale);
        log.info("MSG:::::{}", msg);
        return demoService.findDemoData()
                .map(data -> ResponseEntity.ok(new ApiResult<>(data)));
    }

    @GetMapping("/api/v1/demo/dbdata")
    public Mono<ResponseEntity<ApiResult<DemoDataResponse>>> demoDbData(
            @RequestParam(value = "aaa", required = true) String aaa) {
        return demoService.demoDbData(DemoDataRequest.builder().aaa(aaa).build())
                .map(data -> ResponseEntity.ok(new ApiResult<>(data)));
    }

    @PostMapping("/api/v1/demo/dbdata")
    public Mono<ResponseEntity<ApiResult<DemoDataResponse>>> demoDbData(
            @RequestBody DemoDataRequest demoDataRequest) {
        return demoService.demoDbData(demoDataRequest)
                .map(data -> ResponseEntity.ok(new ApiResult<>(data)));
    }

    @GetMapping("/api/v1/demo/dbdata-query")
    public Mono<ResponseEntity<ApiResult<DemoDataResponse>>> demoDbQueryData(
            @RequestParam(value = "aaa", required = true) String aaa) {
        return demoService.demoDbDataQuery(DemoDataRequest.builder().aaa(aaa).build())
                .map(data -> ResponseEntity.ok(new ApiResult<>(data)));
    }

    @GetMapping("/api/v1/demo/dbdata-custom-query")
    public Mono<ResponseEntity<ApiResult<DemoDataResponse>>> demoDbCustomQueryData(
            @RequestParam(value = "aaa", required = true) String aaa) {
        return demoService.demoDbDataCustomQuery(DemoDataRequest.builder().aaa(aaa).build())
                .map(data -> ResponseEntity.ok(new ApiResult<>(data)));
    }

    @GetMapping("/api/v1/demo/dbdata-custom-query-2")
    public Mono<ResponseEntity<ApiResult<DemoDataListResponse>>> demoDbCustomQueryData(
            @RequestParam(value = "aaa", required = false) String aaa,
            @RequestParam(value = "bbb", required = false) String bbb) {
        return demoService.demoDbDataCustomQuery2(aaa, bbb)
                .map(data -> ResponseEntity.ok(new ApiResult<>(data)));
    }

    @PostMapping("/api/v1/demo/valid-test-post")
    public Mono<ResponseEntity<ApiResult<DemoDataResponse>>> demoValidData1(@Valid @RequestBody DemoValidTestRequest demoValidTestRequest) {
        log.info("data:::::{}", demoValidTestRequest.toString());
        return Mono.just(ResponseEntity.ok(new ApiResult<>(DemoDataResponse.builder().aaa(demoValidTestRequest.getAaa()).bbb(demoValidTestRequest.getBbb()).build())));
    }

    @GetMapping("/api/v1/demo/valid-test-get")
    public Mono<ResponseEntity<ApiResult<DemoDataResponse>>> demoValidData2(
            @NotBlank(message = "A2001:aaa")
            @RequestParam(value = "aaa", required = false)
            String aaa,
            @NotBlank(message = "A2002:bbb,3")
            @Size(min = 3, message = "A2002:bbb,3")
            @RequestParam(value = "bbb", required = false)
            String bbb
    ) {
        log.info("data:::::{},{}", aaa, bbb);
        return Mono.just(ResponseEntity.ok(new ApiResult<>(DemoDataResponse.builder().aaa(aaa).bbb(bbb).build())));
    }

    @GetMapping("/api/v1/demo/valid-test-get-model")
    public Mono<ResponseEntity<ApiResult<DemoDataResponse>>> demoValidData3(@Valid DemoValidTestRequest demoValidTestRequest) {
        log.info("data:::::{}", demoValidTestRequest.toString());
        return Mono.just(ResponseEntity.ok(new ApiResult<>(DemoDataResponse.builder().aaa(demoValidTestRequest.getAaa()).bbb(demoValidTestRequest.getBbb()).build())));
    }

    @GetMapping("/api/v1/demo/custom-exception")
    public Mono<ResponseEntity<ApiResult<DemoDataResponse>>> demoCustomException() {
        if (true) {
            //throw new CustomException(ErrorCode.SYSTEM_ERROR_ADM.getCode());
            //throw new CustomException(ErrorCode.REQUIRED_VALUE.getCode(), new String[]{"aaa"});
            //throw new CustomException(ErrorCode.NOT_ENOUGH_CHARACTERS_SIZE.getCode(), new String[]{"aaa", "2"});
            //return Mono.error(new CustomException(ErrorCode.NOT_ENOUGH_CHARACTERS_SIZE.getCode(), new String[]{"aaa", "2"}));
            //return Mono.error(new CustomException(ErrorCode.SYSTEM_ERROR_ADM.getCode(), "에러당"));
            //return Mono.error(new CustomException(ErrorCode.SYSTEM_ERROR_ADM.getCode(), "error.A0001"));
            //return Mono.error(new CustomException("AAAA", "error.A0001"));
            //return Mono.error(new CustomException("BBBB", "error.A2002", new String[]{"aaa", "2"}));
            //return Mono.error(new CustomException());
            return Mono.error(new CustomException(ErrorCode.SYSTEM_ERROR_ADM.getCode()));
        }
        return Mono.just(ResponseEntity.ok(new ApiResult<>(DemoDataResponse.builder().aaa("a").bbb("b").build())));
    }
}
