package com.recodesolutions.itticket.mapper;

import com.recodesolutions.itticket.dto.RelievingDto;
import com.recodesolutions.itticket.dto.RelievingItemDto;
import com.recodesolutions.itticket.entity.Relieving;
import com.recodesolutions.itticket.entity.RelievingItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",uses = {MappingUtils.class})
public interface RelievingMapper {


    Relieving RelievingDtoToRelieving(RelievingDto RelievingDto);

    @Mapping(source = "accessoryId",target = "accessory",qualifiedByName = "getAccessoryTypeById")
    @Mapping(source = "relieving",target = "relieving",qualifiedByName = "getRelievingById")
    RelievingItem map(RelievingItemDto RelievingItemDto);

    void update(@MappingTarget Relieving Relieving, RelievingDto RelievingItemDto);

}
