package com.sparta.deliverybackend.api.oauth2.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@ConfigurationProperties(prefix = "oauth2")
public class Oauth2Properties {
	private final Map<String, User> user = new HashMap<>();
	private final Map<String, Provider> provider = new HashMap<>();

	@Getter
	@Setter
	public static class User {
		private String clientId;
		private String clientSecret;
		private String redirectUri;
	}

	@Getter
	@Setter
	public static class Provider {
		private String tokenUri;
		private String userInfoUri;
		private String userNameAttribute;
	}
}
