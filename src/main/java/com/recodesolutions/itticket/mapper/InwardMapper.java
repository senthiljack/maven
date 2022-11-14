package com.recodesolutions.itticket.mapper;

import com.recodesolutions.itticket.dto.InwardDto;
import com.recodesolutions.itticket.dto.InwardItemDto;
import com.recodesolutions.itticket.entity.Inward;
import com.recodesolutions.itticket.entity.InwardItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",uses = {MappingUtils.class})
public interface InwardMapper {


    Inward inwardDtoToInward(InwardDto inwardDto);

    @Mapping(source = "accessoryId",target = "accessory",qualifiedByName = "getAccessoryTypeById")
    @Mapping(source = "inward",target = "inward",qualifiedByName = "getInwardById")
    InwardItem map(InwardItemDto inwardItemDto);

    void update(@MappingTarget Inward inward, InwardDto inwardItemDto);

}
