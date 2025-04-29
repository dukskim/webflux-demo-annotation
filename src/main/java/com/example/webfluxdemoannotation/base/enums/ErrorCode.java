package com.example.webfluxdemoannotation.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.util.EnumSet;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    // Error Default
    SYSTEM_ERROR                ("A0001", HttpStatus.INTERNAL_SERVER_ERROR, "error.A0001"),
    SESSION_EXPIRED             ("A0002", HttpStatus.UNAUTHORIZED, "error.A0002"),
    BAD_REQUEST                 ("A0003", HttpStatus.BAD_REQUEST, "error.A0003"),

    SYSTEM_ERROR_ADM            ("A1001", HttpStatus.BAD_REQUEST, "error.A1001"),
    NOT_SUPPORTED               ("A1002", HttpStatus.BAD_REQUEST, "error.A1002"),

    REQUIRED_VALUE              ("A2001", HttpStatus.BAD_REQUEST, "error.A2001"),
    NOT_ENOUGH_CHARACTERS_SIZE  ("A2002", HttpStatus.BAD_REQUEST, "error.A2002"),

    ERR                         ("", HttpStatus.INTERNAL_SERVER_ERROR, "")
    ;

    private String code;
    private HttpStatus status;
    private String msg;

    public static ErrorCode findByCode(final String code) {
        if(!StringUtils.hasText(code)) return null;
        return EnumSet.allOf(ErrorCode.class).stream()
                .filter(e -> e.getCode().equals(code)).findAny().orElse(null);
    }

}