package com.recodesolutions.itticket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnboardItemDto {
    private Long id;
    private String name;
    private String serialNo;
    private Long accessoryId;

    private Long onboard;

    private String othersName;
    private Long qty= Long.valueOf(0);
}
