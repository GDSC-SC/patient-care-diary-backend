package com.springboot.global.exception;

import com.springboot.global.error.ErrorCode;

public class DuplicateRequestException extends BusinessException {
    public DuplicateRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}

