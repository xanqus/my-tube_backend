package com.xaqnus.my_tube_backend.spring_security.domain;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseDTO {
    String code;
    String message;
    HttpStatus status;
}


