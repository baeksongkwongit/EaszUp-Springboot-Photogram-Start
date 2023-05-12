package com.cos.photogramstart.domain.user;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략 데이터베이스를 따라간다.
    private int id;

    @Column(length = 100, unique = true) //Oauth2로그인을 위해 늘림
    private String username;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    private String website;
    private String bio;
    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl;
    private String role;

    //나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼을 만들지마.
    // User를 Selcect  할때 해당 User Id로 등록된 Image 들을 다 가져와
    //Lazy = User를  Select 할때 해당 User Id로 등록된 Image들을 가져오지마 - 대신 getImages()함수가 호출될때 가지고와
    //Eager = User를 select 할때 해당 User id로 등록된 image들을 전부 Join해서 가져와!!

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)  //양방향 매핑
    @JsonIgnoreProperties({"user"}) //images안에 user를 호출 못하게 막는다.
    private List<Image> images;

    private LocalDateTime createDate;

    @PrePersist
    public void setCreateDate(){
        this.createDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", bio='" + bio + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", role='" + role + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
