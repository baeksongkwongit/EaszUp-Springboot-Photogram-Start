package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeResDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    private final EntityManager em; //Repositorry Entitymanager
    @Transactional
    public void 구독하기(int fromUserId, int toUserId) {
        try {
            subscribeRepository.mSubscribe(fromUserId, toUserId);

        }catch (Exception e){
            throw new CustomApiException("이미 구독을 하였습니다.");
        }
    }
    @Transactional
    public void 구독취소하기(int fromUserId, int toUserId){
        subscribeRepository.mUnSubscribe(fromUserId, toUserId);

    }
    @Transactional(readOnly = true)
    public List<SubscribeResDto> 구독리스트(int principalId, int pageUserId){

//        StringBuffer sb = new StringBuffer();
//        sb.append("SELECT u.id, u.USERNAME as userName, u.PROFILEIMAGEURL as profileImageUrl, ");
//        sb.append("IF((SELECT true FROM SUBSCRIBE S WHERE fromUserId=? AND TOUSERID= u.id), TRUE, FALSE) as subscribeState, ");
//        sb.append("IF((?= u.id), TRUE, FALSE) as equalUserState ");
//        sb.append("FROM  USER u INNER JOIN subscribe S ");
//        sb.append("ON u.id = s.toUserId ");
//        sb.append("WHERE s.fromuserId=? ");

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username as userName, u.profileImageUrl as profileImageUrl, ");
        sb.append("if ((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) as subscribeState, ");
        sb.append("if ((?=u.id), 1, 0) as equalUUserState ");
        sb.append("FROM User u INNER JOIN Subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ? "); // 세미콜론 첨부하면 안됨


        //1물음표  principallid
        //2. principallid
        //3. pageUserid

        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);


        //QLRM으로 자바에 매핑 한다.
        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeResDto> subscribeDtos = result.list(query, SubscribeResDto.class);
        System.out.println("쿼리 : "+query.getResultList().get(0));

        return subscribeDtos;
    }
}
