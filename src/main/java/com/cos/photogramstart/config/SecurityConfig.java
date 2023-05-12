package com.cos.photogramstart.config;

import com.cos.photogramstart.config.oauth.OAuth2DetailSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity  //해당파일로 시큐리티 활성화
@Configuration //Ioc
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2DetailSerivce oAuth2DetailSerivce;

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //Supe  삭제 - 기존 시큐리티가 가지고 있는 기능이 비활성화 된다.
        http.authorizeRequests()
                .antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "comment/**","/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/signin")
                .loginProcessingUrl("/auth/signin") //POST -> 스프링 시큐리티가 로그인 프로세스 진행
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login() //form로그인도 하는데 , oauth2로그인도 할꺼야
                .userInfoEndpoint()////oauth2로그인을 하면 최종응답을 회원정보를 바로 받을 수 있다.
                .userService(oAuth2DetailSerivce);
    }
}
