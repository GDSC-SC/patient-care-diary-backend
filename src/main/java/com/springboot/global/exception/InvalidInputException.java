package com.springboot.global.exception;

import com.springboot.global.error.ErrorCode;

public class InvalidInputException extends BusinessException{
    public InvalidInputException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
