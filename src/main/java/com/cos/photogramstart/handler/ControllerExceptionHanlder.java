package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@ControllerAdvice
@RestController
public class ControllerExceptionHanlder {

//    @ExceptionHandler(RuntimeException.class)
//    public String vaildationException(RuntimeException e){
//        return e.getMessage();
//    }

//    @ExceptionHandler(CustomValidationException.class)
//    public Map<String, String> vaildationException(CustomValidationException e){
//        return e.getErrorMap();
//    }

    //CMRespDto<Map<String, String>> -> ? 를 넣어도 됨 그럼 문자열도 리턴 가능함.
//    @ExceptionHandler(CustomValidationException.class)
//    public CMRespDto<Map<String, String>> vaildationException(CustomValidationException e){
//        return new CMRespDto(-1, e.getMessage(), e.getErrorMap());
//    }

    @ExceptionHandler(CustomValidationException.class)
    public String vaildationException(CustomValidationException e){

        if(e.getErrorMap() == null){
            return Script.back(e.getMessage());
        }else{
            return Script.back(e.getErrorMap().toString());

        }
    }

    @ExceptionHandler(CustomException.class)
    public String exception(CustomException e){
            return Script.back(e.getMessage());

    }

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> vaildationException(CustomValidationApiException e){
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> vaildationException(CustomApiException e){
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(),null), HttpStatus.BAD_REQUEST);

    }
}
