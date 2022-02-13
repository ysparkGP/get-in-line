package com.project.getinline.dto;

import com.project.getinline.constant.EventStatus;
import com.project.getinline.constant.PlaceType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public record PlaceRequest(
        @NotBlank PlaceType placeType,
        @NotBlank String placeName,
        @NotNull String address,
        @NotNull String phoneNumber,
        @NotNull @Positive Integer capacity,
        @NotNull String memo
) {
    public static PlaceRequest of(
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            Integer capacity,
            String memo
    ){
        return new PlaceRequest(
                placeType,
                placeName,
                address,
                phoneNumber,
                capacity,
                memo
        );
    }

    public PlaceDTO toDTO(){

        return PlaceDTO.of(
                placeType,
                placeName,
                address,
                phoneNumber,
                capacity,
                memo
        );
    }
}
