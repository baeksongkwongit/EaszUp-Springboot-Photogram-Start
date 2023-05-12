package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//어노테이션이 없어도 Jparepository 를 상속하면 IOC 등록이 자동으로 된다.
public interface UserRepsitory extends JpaRepository<User,Integer> {
    //JPA naming query
    User findByUsername(String username);
}
