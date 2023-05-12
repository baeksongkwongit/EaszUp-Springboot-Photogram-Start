package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomApiException extends RuntimeException{

    private static final long serialVersionUID= 1L;

//    private String message;

    public CustomApiException(String message) {
        super(message);
    }

}
