package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.likes.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;

    @Transactional
    public void 좋아요(int imageId, int pricipalId){
        likesRepository.mLikes(imageId, pricipalId);

    }
    @Transactional
    public void 좋아요취소(int imageId, int pricipalId){
        likesRepository.mUnLikes(imageId,pricipalId);
    }
}
