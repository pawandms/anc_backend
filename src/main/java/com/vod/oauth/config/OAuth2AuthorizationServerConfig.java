package com.vod.oauth.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.vod.oauth.util.JWTTokenEnhancer;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter  {

	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientDetailsService mongoClientDetailsService;

    @Autowired
    private UserDetailsService mongoUserDetailsService;

    @Autowired
    private JWTTokenEnhancer tokenEnhancer;
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
  
    	TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
          Arrays.asList(tokenEnhancer(), accessTokenConverter()));
        
        endpoints.tokenStore(tokenStore())
        .tokenEnhancer(tokenEnhancerChain)
  	    .accessTokenConverter(accessTokenConverter())
  	  .authenticationManager(authenticationManager)
  	  .userDetailsService(mongoUserDetailsService);
    }
    
    
    public TokenEnhancer tokenEnhancer() {
        return tokenEnhancer;
    }
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
   
    
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
      
        /*
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.applyPermitDefaultValues();
        // add allow-origin to the headers
        config.addAllowedHeader("access-control-allow-origin");
        config.addAllowedHeader("Access-Control-Allow-Origin");
        config.addAllowedOrigin("localhost");
        config.addAllowedOrigin("http://namaskar.local");
        config.addAllowedOrigin("https://videohub.live");
        source.registerCorsConfiguration("/oauth/token", config);
        source.registerCorsConfiguration("/**", config);
        CorsFilter filter = new CorsFilter(source);
       
        security.addTokenEndpointAuthenticationFilter(filter);
        
        */
    }
    
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //Using mongodb to save client information
        clients.withClientDetails(mongoClientDetailsService);
    }
    
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
       
    	  JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    	converter.setSigningKey("123");
        return converter;
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
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
    
    

/*    
    @Bean
    public PasswordEncoder userPasswordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

  */ 

   
}
