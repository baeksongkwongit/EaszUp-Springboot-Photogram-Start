package com.cos.photogramstart.config.oauth;

import com.cos.photogramstart.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuth2DetailSerivce extends DefaultOAuth2UserService {

    private final UserRepsitory userRepsitory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("Oauth2서비스 탐");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        Map<String, Object> userInfo = oAuth2User.getAttributes();

        String username = "facebook_" + (String) userInfo.get("id");
        String name = (String)userInfo.get("name");
        String password= new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        String email=(String) userInfo.get("email");

        User userEntity = userRepsitory.findByUsername(username);

        if(userEntity ==null){
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .name(name)
                    .role("ROLE_USER")
                    .build();
            return new PrincipalDetails(userRepsitory.save(user), oAuth2User.getAttributes());

        }else{
            return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
        }

    }
}
