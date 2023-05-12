package com.cos.photogramstart.web;

import com.cos.photogramstart.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ControllerExceptionHanlder;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/","/image/story"})
    public String story(){
        return "image/story";
    }

    //API 구현을 한다면 - 이유 - (브라우저에서 요청하는게 아니라, 안드로이드, IOS 요청)
    @GetMapping({"/image/popular"})
    public String popluar(Model model){
        //api는 데이터를 리턴하는 서버!!
        List<Image> images = imageService.인기사진();

        model.addAttribute("images", images);

        return "image/popluar";
    }
    @GetMapping({"image/upload"})
    public String upload(){
        return "image/upload";
    }

    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        if(imageUploadDto.getFile().isEmpty()){
            throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
        }
        //Service 호출
        imageService.사진업로드(imageUploadDto,principalDetails);
        return "redirect:/user/"+principalDetails.getUser().getId();
    }
}
