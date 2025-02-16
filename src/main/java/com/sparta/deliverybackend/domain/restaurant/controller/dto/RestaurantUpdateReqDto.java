package com.sparta.deliverybackend.domain.restaurant.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestaurantUpdateReqDto {

    private String name;
    private String openTime;
    private String closeTime;
    private int minPrice;

    public RestaurantUpdateReqDto(String name, String openTime, String closeTime, int minPrice){
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minPrice = minPrice;
    }
}
