package com.recodesolutions.itticket.mapper;


import com.recodesolutions.itticket.dto.InwardDto;
import com.recodesolutions.itticket.dto.InwardItemDto;
import com.recodesolutions.itticket.dto.RequestHeaderData;
import com.recodesolutions.itticket.entity.Inward;
import com.recodesolutions.itticket.entity.InwardItem;
import com.recodesolutions.itticket.entity.User;
import com.recodesolutions.itticket.interpreter.AzureOAuthJwtToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    void update(@MappingTarget RequestHeaderData requestHeaderData, User user);

}
