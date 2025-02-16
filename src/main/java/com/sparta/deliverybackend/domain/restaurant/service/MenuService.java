package com.sparta.deliverybackend.domain.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.deliverybackend.api.auth.controller.dto.VerifiedMember;
import com.sparta.deliverybackend.domain.member.entity.Manager;
import com.sparta.deliverybackend.domain.member.repository.ManagerRepository;
import com.sparta.deliverybackend.domain.restaurant.controller.dto.MenuCreateReqDto;
import com.sparta.deliverybackend.domain.restaurant.controller.dto.MenuOptionReqDto;
import com.sparta.deliverybackend.domain.restaurant.controller.dto.MenuRespDto;
import com.sparta.deliverybackend.domain.restaurant.controller.dto.MenuUpdateReqDto;
import com.sparta.deliverybackend.domain.restaurant.entity.CuisineType;
import com.sparta.deliverybackend.domain.restaurant.entity.Menu;
import com.sparta.deliverybackend.domain.restaurant.entity.Restaurant;
import com.sparta.deliverybackend.domain.restaurant.repository.MenuRepository;
import com.sparta.deliverybackend.domain.restaurant.repository.RestaurantRepository;
import com.sparta.deliverybackend.exception.customException.EtcException;
import com.sparta.deliverybackend.exception.customException.NotFoundEntityException;
import com.sparta.deliverybackend.exception.customException.NotHaveAuthorityException;
import com.sparta.deliverybackend.exception.enums.ExceptionCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {

	private final RestaurantRepository restaurantRepository;
	private final MenuRepository menuRepository;
	private final ManagerRepository managerRepository;

	// Restaurant 조회 검증
	private Restaurant findRestaurantOrThrow(Long restaurantId) {
		return restaurantRepository.findById(restaurantId)
			.orElseThrow(() -> new NotFoundEntityException(ExceptionCode.NOT_FOUND_RESTAURANT));
	}

	// Manager 조회 검증
	private Manager findManagerOrThrow(VerifiedMember verifiedMember) {
		return managerRepository.findById(verifiedMember.id())
			.orElseThrow(() -> new NotHaveAuthorityException(ExceptionCode.NOT_HAVE_AUTHORITY_CREATE_MENU));
	}

	// Menu 조회 검증
	private Menu findMenuOrThrow(Long menuId) {
		return menuRepository.findById(menuId)
			.orElseThrow(() -> new NotFoundEntityException(ExceptionCode.NOT_FOUND_MENU));
	}

	// Menu 해당 가게 검증
	private void validateMenuOwnership(Restaurant restaurant, Menu menu) {
		if (!menu.getRestaurant().getId().equals(restaurant.getId())) {
			throw new EtcException(ExceptionCode.NOT_MATCH_RESTAURANT_MENU);
		}
	}

	@Transactional
	public MenuRespDto createMenu(MenuCreateReqDto menuCreateReqDto, VerifiedMember verifiedMember) {
		Restaurant restaurant = findRestaurantOrThrow(menuCreateReqDto.getRestaurantId());
		findManagerOrThrow(verifiedMember);

		Menu menu = Menu.builder()
			.name(menuCreateReqDto.getName())
			.price(menuCreateReqDto.getPrice())
			.description(menuCreateReqDto.getDescription())
			.cuisineType(CuisineType.valueOf(menuCreateReqDto.getCuisineType()))
			.restaurant(restaurant)
			.build();

		menuRepository.save(menu);
		return menu.to();
	}

	@Transactional
	public MenuRespDto updateMenu(Long restaurantId, Long menuId, MenuUpdateReqDto menuUpdateReqDto,
		VerifiedMember verifiedMember) {
		Restaurant restaurant = findRestaurantOrThrow(restaurantId);
		findManagerOrThrow(verifiedMember);

		Menu menu = findMenuOrThrow(menuId);
		validateMenuOwnership(restaurant, menu);

		Menu updateMenu = Menu.builder()
			.id(menu.getId())
			.name(menuUpdateReqDto.getName())
			.price(menuUpdateReqDto.getPrice())
			.description(menuUpdateReqDto.getDescription())
			.cuisineType(CuisineType.valueOf(menuUpdateReqDto.getCuisineType()))
			.restaurant(menu.getRestaurant())
			.build();

		menuRepository.save(updateMenu);
		return updateMenu.to();
	}

	@Transactional
	public void deleteMenu(Long restaurantId, Long menuId, VerifiedMember verifiedMember) {
		Restaurant restaurant = findRestaurantOrThrow(restaurantId);
		findManagerOrThrow(verifiedMember);

		Menu menu = findMenuOrThrow(menuId);
		validateMenuOwnership(restaurant, menu);

		menu.delete();
		menuRepository.save(menu);
	}

	@Transactional(readOnly = true)
	public List<MenuOptionReqDto> getMenuOptions(Long menuId) {
		Menu menu = findMenuOrThrow(menuId);
		return menu.getOptions();
	}

	@Transactional
	public void updateMenuOptions(Long restaurantId, Long menuId, List<MenuOptionReqDto> menuOptionReqDtos,
		VerifiedMember verifiedMember) {
		Restaurant restaurant = findRestaurantOrThrow(restaurantId);
		findManagerOrThrow(verifiedMember);

		Menu menu = findMenuOrThrow(menuId);
		validateMenuOwnership(restaurant, menu);

		menu.updateOptions(menuOptionReqDtos);
		menuRepository.save(menu);
	}
}
