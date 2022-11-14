package com.recodesolutions.itticket.dto;

import com.recodesolutions.itticket.entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class RequestHeaderData {

    private Long id;
    private String email;
    private String fullName;
    private String firstName;
    private String lastName;
    private String token;

}

