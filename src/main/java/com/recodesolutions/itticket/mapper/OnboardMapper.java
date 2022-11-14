package com.recodesolutions.itticket.mapper;

import com.recodesolutions.itticket.dto.OnboardDto;
import com.recodesolutions.itticket.dto.OnboardItemDto;
import com.recodesolutions.itticket.entity.Onboard;
import com.recodesolutions.itticket.entity.OnboardItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",uses = {MappingUtils.class})
public interface OnboardMapper {


    Onboard OnboardDtoToOnboard(OnboardDto OnboardDto);

    @Mapping(source = "accessoryId",target = "accessory",qualifiedByName = "getAccessoryTypeById")
    @Mapping(source = "onboard",target = "onboard",qualifiedByName = "getOnboardById")
    OnboardItem map(OnboardItemDto OnboardItemDto);

    void update(@MappingTarget Onboard Onboard, OnboardDto OnboardItemDto);

}
