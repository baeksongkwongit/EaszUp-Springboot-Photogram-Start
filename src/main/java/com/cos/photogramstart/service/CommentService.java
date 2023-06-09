package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepsitory;
import com.cos.photogramstart.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepsitory userRepsitory;

    @Transactional
    public Comment 댓글쓰기(String content, int imageId, int userId){
        ///Tip
        //대신 return 시에 image 객체와 user 객체는 id 값만을 가지고 있는 빈 객체를 리턴 받는다.
        Image image = new Image();
        image.setId(imageId);

       //User는  Repository 로 가져와야 한다.
//        User user = new User();
//        user.setId(userId);
        User userEntity = userRepsitory.findById(userId).orElseThrow(()->{
           throw new CustomApiException("유저 아이드를 찾을 수 없습니다.");
        });


        Comment comment = new Comment();

        comment.setContent(content);
        comment.setImage(image);
        comment.setUser(userEntity);

        return  commentRepository.save(comment);
    }
    @Transactional
    public void 댓글삭제(int id){
        try{
            commentRepository.deleteById(id);
        }catch (Exception e){
            throw new CustomApiException(e.getMessage());
        }

    }
}
