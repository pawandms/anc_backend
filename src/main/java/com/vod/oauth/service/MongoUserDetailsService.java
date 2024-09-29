package com.vod.oauth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vod.oauth.model.UserAuth;

@Service
public class MongoUserDetailsService implements UserDetailsService {

	private final String USER_CONLLECTION = "userAuth";

    @Autowired
    MongoTemplate mongoTemplate;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Identifier: 1 mobile number 2 email 3 user name 4qq 5 wechat 6 Tencent Weibo 7 Sina Weibo
        UserAuth userAuth = mongoTemplate.findOne(new Query(Criteria.where("identifier").is(username)), UserAuth.class, USER_CONLLECTION);
        if(userAuth == null) {
            throw new RuntimeException ("no user information is found");
        }
        User user=  new User(username, userAuth.getCertificate(), userAuth.isEnabled(), userAuth.isAccountNonExpired(), 
        		userAuth.isCredentialsNonExpired(), userAuth.isAccountNonLocked(), mapToGrantedAuthorities(userAuth.getRoles()));
       
        return user;
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
