package com.team1.finalproject.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GlobalException extends RuntimeException{
    private final ErrorCode errorCode;
}
