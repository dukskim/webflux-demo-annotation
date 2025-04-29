/**
 * 유효성 검증 에러 (RestControllerAdvice)
 * @Validated, @Valid
 * 컨트롤러의 요청 값 체크 시 Exception 핸들링
 */
package com.example.webfluxdemoannotation.core.exception;

import com.example.webfluxdemoannotation.base.dto.ApiResult;
import com.example.webfluxdemoannotation.base.enums.ErrorCode;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ValidExceptionHandler {

    private final MessageSource messageSource;

    /**
     * DTO 유효성 체크 실패 (RequestBody, ModelAttribute 체크)
     * @param ex
     * @param exchange
     * @param locale
     * @return
     */
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ApiResult>> handleBindException(WebExchangeBindException ex, ServerWebExchange exchange, Locale locale) {
//        log.info("유효성검증 DTO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        String validMessage = ex.getFieldErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Validation failed");

        return this.validExceptionHandler(validMessage, locale);
    }

    /**
     * 주소 파라미터 유효성 체크 실패 (@RequestParam, @PathVariable 체크)
     * @param ex
     * @param exchange
     * @param locale
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Mono<ResponseEntity<ApiResult>> handleConstraintViolation(ConstraintViolationException ex, ServerWebExchange exchange, Locale locale) {
//        log.info("유효성검증 파라미터 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        String validMessage = ex.getMessage();

        if (validMessage != null && validMessage.contains(":")) {
            validMessage = validMessage.substring(validMessage.indexOf(":") + 1).trim();
        }

        return this.validExceptionHandler(validMessage, locale);
    }

    /**
     * 유효성 실패 시 응답 코드와 메시지를 매핑한다.
     * @param validMessage
     * @param locale
     * @return
     */
    private Mono<ResponseEntity<ApiResult>> validExceptionHandler(String validMessage, Locale locale) {

        String[] validMessages = validMessage.split(":");
//        for (String string : validMessages) {
//            log.info("!!!!!!!!!!!: {}", string);
//        }
        String code;
        String errorMsgCode;
        String subMessage = "";
        String localeMessage;

        ErrorCode errorCode;
        if(validMessages.length > 1) {
            errorCode = ErrorCode.findByCode(validMessages[0].trim());
            subMessage = validMessages[1].trim();
        } else {
            errorCode = ErrorCode.findByCode(validMessage.trim());
        }

        if (errorCode == null) {
            errorCode = ErrorCode.BAD_REQUEST;
        }
        code = errorCode.getCode();
        errorMsgCode = errorCode.getMsg();
//        log.info("ffffffffffffffff: {}", errorCode.getMsg());
        localeMessage = messageSource.getMessage(errorMsgCode, null, locale);
//        log.info(localeMessage);

        if(localeMessage.indexOf("{") > -1) {
            String[] arrSubMessage = subMessage.split(",");
            for (int i = 0; i < arrSubMessage.length; i++) {
                localeMessage = localeMessage.replace("{"+i+"}", arrSubMessage[i]);
            }
        } else {
            subMessage = subMessage != "" ? "(" + subMessage + ")" : "";
            localeMessage += subMessage;
        }
        ApiResult apiResult = new ApiResult(code, localeMessage);

//        log.info("[Error] validException   Code: {} , Msg: {}", errorCode, apiResult.getMsg());

        return Mono.just(ResponseEntity.status(errorCode.getStatus()).body(apiResult));
    }
}
