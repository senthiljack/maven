package com.recodesolutions.itticket.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailPayload {

    private String[] sendTo;
    private String sendFrom;
    private String token;
    private String subject;
    private Object data;
    private String templateReference;
    private String templateLocation;

}
