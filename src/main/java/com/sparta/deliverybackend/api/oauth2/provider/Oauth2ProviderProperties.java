package com.sparta.deliverybackend.api.oauth2.provider;

import com.sparta.deliverybackend.api.oauth2.config.Oauth2Properties;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Oauth2ProviderProperties {
	private final String clientId;
	private final String clientSecret;
	private final String redirectUrl;
	private final String tokenUrl;
	private final String userInfoUrl;

	public Oauth2ProviderProperties(Oauth2Properties.User user, Oauth2Properties.Provider provider) {
		this(user.getClientId(), user.getClientSecret(), user.getRedirectUri(), provider.getTokenUri(),
			provider.getUserInfoUri());
	}

	@Builder
	public Oauth2ProviderProperties(String clientId, String clientSecret, String redirectUrl, String tokenUrl,
		String userInfoUrl) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUrl = redirectUrl;
		this.tokenUrl = tokenUrl;
		this.userInfoUrl = userInfoUrl;
	}
}
