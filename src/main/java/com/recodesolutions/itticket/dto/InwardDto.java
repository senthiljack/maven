package com.recodesolutions.itticket.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.recodesolutions.itticket.entity.InwardItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InwardDto {

    private Long id;
    private LocalDateTime issuedDate;
    private Long providerId;
    private String providerName;
    private Boolean others;
    private String name;
    private String vendorName;
    private Long receiverId;
    private String receiverName;
    private String providerSignature;
    private String receiverSignature;
    private List<InwardItemDto> inwardItems;

}
