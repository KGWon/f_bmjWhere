package com.example.bmjwhere.service;

import com.example.bmjwhere.dto.PageRequestDTO;
import com.example.bmjwhere.dto.PageResultDTO;
import com.example.bmjwhere.dto.ResturantDTO;
import com.example.bmjwhere.dto.ResturantImageDTO;
import com.example.bmjwhere.entity.Resturant;
import com.example.bmjwhere.entity.ResturantImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ResturantService {
    Long register(ResturantDTO resturantDTO);
    ResturantDTO getResturant(Long rno);
    PageResultDTO<ResturantDTO, Object[]> getList(PageRequestDTO requestDTO);
    PageResultDTO<ResturantDTO, Object[]> getJList(PageRequestDTO requestDTO);
    void removeWithReplise(Long mno);  
    void modify(ResturantDTO resturantDTO);

    default ResturantDTO entitiesToDTO(Resturant resturant, List<ResturantImage> resturantImages, Double avg, Long reviewCnt){
        ResturantDTO resturantDTO = ResturantDTO.builder()
                .rno(resturant.getRno())
                .title(resturant.getTitle())
                .address(resturant.getAddress())
                .phoneNumber(resturant.getPhoneNumber())
                .type2((resturant.getType2()))
                .operatingTime(resturant.getOperatingTime())
                .regDate(resturant.getRegDate())
                .modDate(resturant.getModDate())
                .build();
        List<ResturantImageDTO> resturantImageDTOList = resturantImages.stream().
                map(resturantImage -> {
                    return ResturantImageDTO.builder().imgName(resturantImage.getImgName())
                            .path(resturantImage.getPath())
                            .uuid(resturantImage.getUuid())
                            .build();
                }).collect(Collectors.toList());

        resturantDTO.setImageDTOList(resturantImageDTOList);
        if(avg == null) avg = 0.0;
        if(reviewCnt == null) reviewCnt = 0L;
        resturantDTO.setAvg(avg);
        resturantDTO.setReviewCnt(reviewCnt.intValue());
        return resturantDTO;
    }


    default Map<String, Object> dtoToEntity(ResturantDTO resturantDTO){
        Map<String, Object> entityMap = new HashMap<>();

        Resturant resturant = Resturant.builder()
                .rno(resturantDTO.getRno())
                .title(resturantDTO.getTitle())
                .address(resturantDTO.getAddress())
                .phoneNumber(resturantDTO.getPhoneNumber())
                .type2(resturantDTO.getType2())
                .operatingTime(resturantDTO.getOperatingTime())
                .build();
        entityMap.put("resturant", resturant);

        List<ResturantImageDTO> imageDTOList = resturantDTO.getImageDTOList();

        if ((imageDTOList != null && imageDTOList.size() > 0)) {

            List<ResturantImage> resturantImageList = imageDTOList.stream().map(resturantImageDTO -> {
                ResturantImage resturantImage = ResturantImage.builder()
                        .path(resturantImageDTO.getPath())
                        .imgName(resturantImageDTO.getImgName())
                        .uuid(resturantImageDTO.getUuid())
                        .resturant(resturant)
                        .build();
                return resturantImage;
            }).collect(Collectors.toList());
            entityMap.put("imgList", resturantImageList);
        }
        return entityMap;
    }
}
