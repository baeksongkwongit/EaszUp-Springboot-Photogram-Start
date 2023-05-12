package com.cos.photogramstart.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    @Query(value = "SELECT * FROM IMAGE WHERE userId IN(SELECT TOUSERID FROM SUBSCRIBE WHERE FROMUSERID=:principalId)", nativeQuery = true)
    Page<Image> mStory(int principalId, Pageable pageable);

    @Query(value ="SELECT i.* FROM image i INNER JOIN (select imageId, count(imageId) as likeCount from likes group by imageId) c ON i.id = c.imageid ORDER BY LIKECOUNT desc" , nativeQuery = true)
    List<Image> mPopluar();
}
