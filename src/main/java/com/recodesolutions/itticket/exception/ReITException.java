package com.recodesolutions.itticket.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReITException extends RuntimeException{
    private String code;
    private String errorMessage;
}
