package com.cos.photogramstart.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    //return을 못받기 때문에 사용할 수 없다.
//    @Modifying
//    @Query(value ="INSERT INTO comment(content, imageId, userId, createDate) VALUES (:content, :imageId, :userId, now())" ,nativeQuery = true)
//    Comment mSave(String content, int imageId, int userId);

}
