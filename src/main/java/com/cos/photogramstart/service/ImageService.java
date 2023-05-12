package com.cos.photogramstart.service;

import com.cos.photogramstart.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional(readOnly=true) //영속성 컨텍스트 변경 감지를 해서, 터티체킹, flush 반영)
    public Page<Image> 이미지스토리(int principalId, Pageable pageable){
        Page<Image> images = imageRepository.mStory(principalId, pageable);

        //2(cos) 로그인
        //images에 좋아요 상태 담기
        images.forEach((image)->{
            image.setLikeCount(image.getLikes().size());

            image.getLikes().forEach((like)->{
                if(like.getUser().getId() ==principalId){ //해당 이미지에 좋아요한 사람들을 찾아서 현재 로긴한 사람이 좋아요 한것인지 비교
                    image.setLikesStat(true);
                }
            });
        });


        return  images;
    }
    @Value("${file.path}")
    private String uploadFolder;
    @Transactional
    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails){
        UUID uuid = UUID.randomUUID();

        String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename();
        System.out.println("이미지파일이름:"+ imageFileName);
        Path imageFilePath = Paths.get(uploadFolder+imageFileName);

        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        }catch (Exception e){
            e.getStackTrace();
        }
        //image 테이블에 저장
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
        Image imageEntity = imageRepository.save(image);

//        System.out.println(imageEntity);
    }

    @Transactional(readOnly = true)
    public List<Image> 인기사진(){

        return imageRepository.mPopluar();
    }
}
