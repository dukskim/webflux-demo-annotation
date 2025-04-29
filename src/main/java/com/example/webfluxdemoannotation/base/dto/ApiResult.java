package com.example.webfluxdemoannotation.base.dto;

import com.example.webfluxdemoannotation.base.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResult<T> {
    private ResultCode result;
    private String code;
    private String msg;
    private T data;

    public ApiResult() {
        this.result = ResultCode.SUCCESS;
    }

    public ApiResult(T data) {
        this.result = ResultCode.SUCCESS;
        this.data = data;
    }

    public ApiResult(String code, String msg) {
        this.result = ResultCode.ERROR;
        this.code = code;
        this.msg = msg;
    }
}
