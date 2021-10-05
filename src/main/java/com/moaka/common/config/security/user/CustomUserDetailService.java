package com.moaka.common.config.security.user;

import com.moaka.dto.JwtUser;
import com.moaka.mapper.AuthMapper;
import com.moaka.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        JwtUser users = userMapper.retrieveJwtUserById(id);
        if(users != null && users.getUsername().equals(id)) {
            System.out.println("**************Found user***************");
            System.out.println("id : " + users.getUsername());
            users.setRoles(Collections.singletonList("ROLE_USER"));
            return users;
        } else {
            throw new UsernameNotFoundException("id " + id + " not found");
        }
    }
}
