package com.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@ComponentScan(basePackages = {"com.gtd", "com.vod"})
@SpringBootApplication
@EnableCaching
public class MsgAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsgAppApplication.class, args);
	}
	
	 @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }


}
