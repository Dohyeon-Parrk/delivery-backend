package com.sparta.deliverybackend.domain.restaurant.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.deliverybackend.api.controller.dto.VerifiedMember;
import com.sparta.deliverybackend.domain.restaurant.controller.dto.RestaurantCreateRepDto;
import com.sparta.deliverybackend.domain.restaurant.controller.dto.RestaurantCreateReqDto;
import com.sparta.deliverybackend.domain.restaurant.service.RestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/restaurant")
    public ResponseEntity<RestaurantCreateRepDto> createRestaurant(@RequestBody RestaurantCreateReqDto reqDto, VerifiedMember verifiedMember){//로그인 유저로 바꿀 예정
        RestaurantCreateRepDto response = restaurantService.createRestaurant(reqDto, verifiedMember);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

//    @GetMapping("/restaurant")
//    public ResponseEntity<List<RestaurantCreateRepDto>> getRestaurants(VerifiedMember verifiedMember){
//        List<RestaurantCreateRepDto> response = restaurantService.getRestaurants(verifiedMember);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(response);
//    }
}
