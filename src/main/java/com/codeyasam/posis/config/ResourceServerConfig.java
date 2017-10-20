package com.codeyasam.posis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableWebSecurity
@EnableResourceServer
@Profile(value=Profiles.PRODUCTION)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	private static final String RESOURCE_ID = "posis";
	private static final String SIGNING_KEY = "secret";
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin();
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.requestMatchers()
				.antMatchers("/**")
					.and()
				.authorizeRequests()
					.anyRequest().authenticated();

	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources
			.resourceId(RESOURCE_ID)
			.tokenServices(tokenServices());
	}
	
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(SIGNING_KEY);
		return converter;
	}
}

@Configuration
@EnableWebSecurity
@Profile(value={Profiles.DEVELOPMENT, Profiles.TEST})
class DevResourceServer extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin();
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.authorizeRequests()
				.antMatchers("/**").permitAll();
	}
}

class Profiles {
	public static final String DEVELOPMENT = "dev";
	public static final String PRODUCTION  = "prod";
	public static final String TEST = "test";
}
