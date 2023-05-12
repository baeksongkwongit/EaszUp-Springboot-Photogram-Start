package com.cos.photogramstart.domain.likes;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="likes_uk",
                        columnNames = {"imageId", "userId"}
                )
        }
)
public class Likes { //N
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "imageId")
    @ManyToOne
    private Image image; //1

    //무한참조
    @JsonIgnoreProperties("{images}")
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user; //1


    //native query 쓰면 의미가 없다.
//    private LocalDateTime createDate;
//
//    @PrePersist
//    public void setCreateDate(){
//        this.createDate = LocalDateTime.now();
//    }

}
