package com.vod.oauth.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("resource-server-rest-api");
	}

	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		.httpBasic()
		.disable()
		.csrf().disable()
		.anonymous().and().authorizeRequests().antMatchers("/user/**",  "/api/**").authenticated()
		.and().authorizeRequests().antMatchers("backend/api/**").authenticated()
		.antMatchers("/public/**", "/api/media/**", "/content/**").permitAll();
	}
}

