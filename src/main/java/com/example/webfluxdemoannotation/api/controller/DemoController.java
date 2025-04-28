package com.example.webfluxdemoannotation.api.controller;

import com.example.webfluxdemoannotation.api.dto.request.DemoDataRequest;
import com.example.webfluxdemoannotation.api.dto.response.DemoDataResponse;
import com.example.webfluxdemoannotation.api.service.DemoService;
import com.example.webfluxdemoannotation.base.dto.SingleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DemoController {

    private final DemoService demoService;

    private final MessageSource messageSource;

    @GetMapping("/api/v1/demo/data")
    public Mono<ResponseEntity<SingleResponse<DemoDataResponse>>> demoData(Locale locale) {
        String msg = messageSource.getMessage("DEF_SYS", null, locale);
        log.info("MSG:::::{}", msg);
        return demoService.findDemoData()
                .map(data -> ResponseEntity.ok(new SingleResponse<>(data)));
    }

    @GetMapping("/api/v1/demo/dbdata")
    public Mono<ResponseEntity<SingleResponse<DemoDataResponse>>> demoDbData(
            @RequestParam(value = "aaa", required = true) String aaa) {
        return demoService.demoDbData(DemoDataRequest.builder().aaa(aaa).build())
                .map(data -> ResponseEntity.ok(new SingleResponse<>(data)));
    }

    @PostMapping("/api/v1/demo/dbdata")
    public Mono<ResponseEntity<SingleResponse<DemoDataResponse>>> demoDbData(
            @RequestBody DemoDataRequest demoDataRequest) {
        return demoService.demoDbData(demoDataRequest)
                .map(data -> ResponseEntity.ok(new SingleResponse<>(data)));
    }

    @GetMapping("/api/v1/demo/dbdata-query")
    public Mono<ResponseEntity<SingleResponse<DemoDataResponse>>> demoDbQueryData(
            @RequestParam(value = "aaa", required = true) String aaa) {
        return demoService.demoDbDataQuery(DemoDataRequest.builder().aaa(aaa).build())
                .map(data -> ResponseEntity.ok(new SingleResponse<>(data)));
    }

    @GetMapping("/api/v1/demo/dbdata-custom-query")
    public Mono<ResponseEntity<SingleResponse<DemoDataResponse>>> demoDbCustomQueryData(
            @RequestParam(value = "aaa", required = true) String aaa) {
        return demoService.demoDbDataCustomQuery(DemoDataRequest.builder().aaa(aaa).build())
                .map(data -> ResponseEntity.ok(new SingleResponse<>(data)));
    }
}
