package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {

    @Modifying //#INSERT, DELETE, UPDATE 를 네이티브 쿼리로 작성하려면 해당 어노테이션 필요!!!
    @Query(value = "INSERT INTO subscribe(fromuserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
    void mSubscribe(int fromUserId, int toUserId); //성공 : 1(변경된 행의 갯수 만큼), 실패 : -1

    @Modifying
    @Query(value = "DELETE FROM subscribe WHERE fromUserId= :fromUserId AND toUserId =:toUserId", nativeQuery = true)
    void mUnSubscribe(int fromUserId, int toUserId);


    @Query(value = "SELECT count(*) FROM SUBSCRIBE  WHERE FROMUSERID=:pricipailId  AND TOUSERID=:pageUserId", nativeQuery = true)
    int mSubscripbeState(int pricipailId, int pageUserId);

    @Query(value = "SELECT count(*) FROM SUBSCRIBE WHERE FROMUSERID=:pageUserId", nativeQuery = true)
    int mSubscripbeCount (int pageUserId);

}
