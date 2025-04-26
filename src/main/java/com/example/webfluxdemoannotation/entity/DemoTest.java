package com.example.webfluxdemoannotation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class DemoTest {
    @Id
    private String aaa;
    private String bbb;
}
