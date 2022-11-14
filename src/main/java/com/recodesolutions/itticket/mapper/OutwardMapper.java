package com.recodesolutions.itticket.mapper;

import com.recodesolutions.itticket.dto.OutwardDto;
import com.recodesolutions.itticket.dto.OutwardItemDto;
import com.recodesolutions.itticket.entity.Outward;
import com.recodesolutions.itticket.entity.OutwardItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",uses = {MappingUtils.class})
public interface OutwardMapper {


    Outward OutwardDtoToOutward(OutwardDto OutwardDto);

    @Mapping(source = "accessoryId",target = "accessory",qualifiedByName = "getAccessoryTypeById")
    @Mapping(source = "outward",target = "outward",qualifiedByName = "getOutwardById")
    OutwardItem map(OutwardItemDto OutwardItemDto);

    void update(@MappingTarget Outward Outward, OutwardDto OutwardItemDto);

}
