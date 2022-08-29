package com.xaqnus.hyper_x_backend.spring_security.domain;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseDTO {
    String code;
    String message;
    HttpStatus status;
}


