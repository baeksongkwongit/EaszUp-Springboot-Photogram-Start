package com.cos.photogramstart.web.dto.comment;

import jdk.jfr.MemoryAddress;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


//NotnUll = Null값 체크
// NotEmpty = 빈값이거나  Null 체크
//NotBlank = 빈값이거나 Null  그리고 빈공백(스페이시까지)
@Data
public class CommentDto {
    @NotBlank //빈값이거나 null 빈공백 체크
    private String content;
    @NotNull //빈값이거나 null 체크
    private Integer imageId;
    //toEntity 가 필요 없다.
}
