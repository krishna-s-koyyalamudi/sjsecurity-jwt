package com.ksk.sjsecurityjwt.service;

import com.ksk.sjsecurityjwt.entity.UserInfo;
import com.ksk.sjsecurityjwt.mapper.UserDetailsMapper;
import com.ksk.sjsecurityjwt.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserInfoDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByName(username);
        return userInfo.map(UserDetailsMapper::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
}
