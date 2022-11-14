package com.recodesolutions.itticket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelievingDto {

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
    private List<RelievingItemDto> relievingItems;

}
