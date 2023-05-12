package com.cos.photogramstart.auth;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//1. 패스워드는 알아서 체킹하니까 신경쓸 필요 없다.
//2. 리턴이 잘되면 자동으로 세션을 만든다.
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepsitory userRepsitory;

    public PrincipalDetailsService(UserRepsitory userRepsitory) {
        this.userRepsitory = userRepsitory;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("나 실행되??"+ username);
        User userEntity = userRepsitory.findByUsername(username);

        if(userEntity ==null){
            return null;
        }else{
            return new PrincipalDetails(userEntity);
        }

    }
}
