package com.example.bmjwhere.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data   //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor를 합쳐 둔 어노테이션
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResturantDTO {

    private Long rno;

    private String title;

    private String address;

    private String phoneNumber;

    private String type2;

    private String operatingTime;

    // 특정 필드/매개변수가 빌드 세션 중에 설정되지 않으면 항상 0/null/false가 됩니다.
    // @Builder.Default: 필드의 기본값을 지정할 때 사용
    // 여기서는 맛집 이미지의 데이터를 List로 받기 위함, 즉 영화 1개에 여러개의 영화 이미지를 받기 위함



    @Builder.Default
    private List<ResturantImageDTO> imageDTOList = new ArrayList<>();

    //    식당의 평균평점
    private double avg;
    //리뷰수 jpa의 count()
    private int reviewCnt;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}