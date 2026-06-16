package com.ifpr.backend.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseError {
    private int status;
    private String messages;
    private LocalDateTime datetime;
}
