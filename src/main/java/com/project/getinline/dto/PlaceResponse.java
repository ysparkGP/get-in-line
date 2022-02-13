package com.project.getinline.dto;

import com.project.getinline.constant.EventStatus;
import com.project.getinline.constant.PlaceType;
import com.project.getinline.domain.Place;

import java.time.LocalDateTime;

public record PlaceResponse(
        PlaceType placeType,
        String placeName,
        String address,
        String phoneNumber,
        Integer capacity,
        String memo
) {
    public static PlaceResponse of(
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            Integer capacity,
            String memo
    ) {
        return new PlaceResponse(placeType, placeName, address,phoneNumber,capacity,memo);
    }

    public static PlaceResponse from(PlaceDTO placeDTO){
        if(placeDTO == null){
            return null;
        }

        return PlaceResponse.of(
                placeDTO.placeType(),
                placeDTO.placeName(),
                placeDTO.address(),
                placeDTO.phoneNumber(),
                placeDTO.capacity(),
                placeDTO.memo()
        );
    }
}
