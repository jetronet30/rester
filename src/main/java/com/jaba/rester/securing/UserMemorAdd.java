package com.jaba.rester.securing;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.jaba.rester.models.UserModel;
import com.jaba.rester.repos.UserRepo;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserMemorAdd implements UserDetailsService{
    private final UserRepo uRepo;

    @Override
    public UserDetails loadUserByUsername(String username){
        UserModel user = uRepo.findByUsername(username);
        UserDetails userdDetails = User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .roles("USER")
        .build();
        return userdDetails; 
    }
}
