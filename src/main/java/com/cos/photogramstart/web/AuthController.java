package com.cos.photogramstart.web;

import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor //final DI할때 사용
@Controller//1. Ioc, 2.파일을 리턴하는 컨트롤러
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private final AuthService authService;

//    public AuthController(AuthService authService) {
//        this.authService = authService;
//    }

    @GetMapping("/auth/signin")
    public String signinForm(){
        return "auth/signin";
    }
    @GetMapping("/auth/signup")
    public String signupPage() {
        return "auth/signup";
    }

    //회원가입버튼 -> /auth/signup -> /auth/signin\
    @Transactional //write(insert, update, delete)
    @PostMapping("/auth/signup")
    public String signUp (@Valid SignupDto signupDto, BindingResult bindingResult){ //key=vale (x-www-form-urlencoded)

//        if(signupDto.getUsername().length() >20){
//            System.out.println("20자가 넘었어요!!!");
//        }
//ValidationAdvice 로 이관
//        if(bindingResult.hasErrors()){
//            Map<String, String> errorMap = new HashMap<>();
//
//            for(FieldError error : bindingResult.getFieldErrors()){
//                errorMap.put(error.getField(), error.getDefaultMessage());
////                System.out.println(error.getDefaultMessage());
//            }
//
////            return "오류남";
//            throw new RuntimeException("유효성검사 실패함");


        log.info(signupDto.toString());
        User user = signupDto.toEntity();
        User userEntity = authService.회원가입(user);
        log.info(user.toString());
        System.out.println(userEntity);
        return "auth/signin";

    }
}
