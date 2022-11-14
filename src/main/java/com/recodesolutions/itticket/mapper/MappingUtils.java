package com.recodesolutions.itticket.mapper;

import com.recodesolutions.itticket.constants.ErrorCode;
import com.recodesolutions.itticket.constants.ErrorMessages;
import com.recodesolutions.itticket.entity.*;
import com.recodesolutions.itticket.exception.ReITException;
import com.recodesolutions.itticket.repository.*;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MappingUtils {

    private final AccessoryTypeRepository accessoryTypeRepository;
    private final InwardRepository inwardRepository;
    private final OutwardRepository outwardRepository;
    private final OnboardRepository onboardRepository;
    private final RelievingRepository relievingRepository;

    @Named("getAccessoryTypeById")
    @Cacheable("accessoryId")
    public AccessoryType getAccessoryTypeById(Long id){
        return accessoryTypeRepository.findById(id).get();
    }


    @Named("getInwardById")
    @Cacheable("inwardId")
    public Inward getInwardById(Long id){

        if(id!=null){
            return inwardRepository.findById(id).orElseThrow(()-> new ReITException(ErrorCode.INWARD_NOT_FOUND, ErrorMessages.INWARD_NOT_FOUND));
        }else{
            return null;
        }
    }

    @Named("getOutwardById")
    @Cacheable("outwardId")
    public Outward getOutwardById(Long id){

        if(id!=null){
            return outwardRepository.findById(id).orElseThrow(()-> new ReITException(ErrorCode.OUTWARD_NOT_FOUND, ErrorMessages.OUTWARD_NOT_FOUND));
        }else{
            return null;
        }
    }

    @Named("getOnboardById")
    @Cacheable("onboardId")
    public Onboard getOnboardById(Long id){

        if(id!=null){
            return onboardRepository.findById(id).orElseThrow(()-> new ReITException(ErrorCode.ONBOARD_NOT_FOUND, ErrorMessages.ONBOARD_NOT_FOUND));
        }else{
            return null;
        }
    }

    @Named("getRelievingById")
    @Cacheable("relievingId")
    public Relieving getRelievingById(Long id){

        if(id!=null){
            return relievingRepository.findById(id).orElseThrow(()-> new ReITException(ErrorCode.RELIEVING_NOT_FOUND, ErrorMessages.RELIEVING_NOT_FOUND));
        }else{
            return null;
        }
    }
}
