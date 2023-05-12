package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service //1. Ioc 2.트랜잭션 관리
public class AuthService {

    private final UserRepsitory userRepsitory;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public User 회원가입(User user){
        //회원가입을 진행
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        user.setPassword(encPassword);
        user.setRole("ROLE_USER");

        User userEntity = userRepsitory.save(user);
        return userEntity;
    }

}
