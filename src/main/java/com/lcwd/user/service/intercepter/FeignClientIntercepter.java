package com.lcwd.user.service.intercepter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
@Component
public class FeignClientIntercepter implements RequestInterceptor{

	@Autowired		// To autowired it first need to create bean of this
	private OAuth2AuthorizedClientManager manager;  // (Responsible for overall management of Authorized Client  ) 
	
	@Override
	public void apply(RequestTemplate template) {

		String token = manager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client").principal("internal").build()).getAccessToken().getTokenValue() ;		// We are giving Registration Id in this .athorize
																					// "my-internal-client": we have already created in .yml file . 					
		template.header("Authorization","Bearer"+token);
	
	}

}
