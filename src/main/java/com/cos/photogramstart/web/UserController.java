package com.cos.photogramstart.web;

import com.cos.photogramstart.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{pageUserId}")
    public String profile(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails ,Model model){
        UserProfileDto dto =  userService.회원프로필(pageUserId, principalDetails.getUser().getId());

        model.addAttribute("dto", dto);
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String updateForm(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails){

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        PrincipalDetails mPrinipalDetails = (PrincipalDetails) authentication.getPrincipal();

//        System.out.println("세션 정보 " + principalDetails.getUsername());
//
//        System.out.println("세션 정보 : " + mPrinipalDetails.getUser());

//        model.addAttribute("principal", principalDetails.getUser());

        return "user/update";
    }
}
