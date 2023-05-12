package com.cos.photogramstart.handler.aop;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component //REstController, Service 모든 것들이 Component로 상속해서 만들어졍 있음,
@Aspect
public class ValidationAdvice {
    @Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("web Api 컨트롤러===============================");

        Object[] args = proceedingJoinPoint.getArgs();
        for(Object arg:args){
            if(arg instanceof BindingResult){
                System.out.println("유효성 검사 하는 함수 입니다.");
                BindingResult bindingResult = (BindingResult) arg;

                if(bindingResult.hasErrors()){
                    Map<String, String > errorMap = new HashMap<>();
                    for(FieldError error: bindingResult.getFieldErrors()){
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
                }
            }
        }
        //proceedingJoinPoint -> profile 함수의 모든 곳에 접근할 수 있는 변수
        //profile함수보다 먼저 실행
        return proceedingJoinPoint.proceed(); //profile함수가 실행됨.
    }
    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("web 컨트롤러===============================");
        Object[] args = proceedingJoinPoint.getArgs();
        for(Object arg:args){
            if(arg instanceof BindingResult) {

                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
//                System.out.println(error.getDefaultMessage());
                    }

//            return "오류남";
                    throw new RuntimeException("유효성검사 실패함");

                }
            }
        }

        return proceedingJoinPoint.proceed();
    }
}

