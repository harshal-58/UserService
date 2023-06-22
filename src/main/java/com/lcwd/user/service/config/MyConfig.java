package com.lcwd.user.service.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import com.lcwd.user.service.intercepter.RestTemplateInterceptor;

@Configuration
public class MyConfig {

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;
	
	@Autowired
	private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;
	
//	@Bean
//	@LoadBalanced
//	public RestTemplate restTemplate() {
//		return new RestTemplate();
//	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		
		RestTemplate restTemplate = new RestTemplate();
		
		interceptors.add(new RestTemplateInterceptor(manager(
				clientRegistrationRepository,oAuth2AuthorizedClientRepository
				)));
		
		restTemplate.setInterceptors(interceptors);
		
		return restTemplate;
	}
	
	// declare the bean of OAuth2AuthorizedClientManager 
	
	@Bean
	public OAuth2AuthorizedClientManager manager(
			ClientRegistrationRepository clientRegistrationRepository,
			OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository
			) {

		OAuth2AuthorizedClientProvider provider= OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();  // It will create ClinetProvider
		
		DefaultOAuth2AuthorizedClientManager clientManager = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, oAuth2AuthorizedClientRepository); 
		
		clientManager.setAuthorizedClientProvider(provider);
		
		return clientManager;
				
	}
	
}
