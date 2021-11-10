package com.example.bmjwhere.service;

import com.example.bmjwhere.dto.PageRequestDTO;
import com.example.bmjwhere.dto.PageResultDTO;
import com.example.bmjwhere.dto.ResturantDTO;
import com.example.bmjwhere.entity.Resturant;
import com.example.bmjwhere.entity.ResturantImage;
import com.example.bmjwhere.repository.ResturantImageRepository;
import com.example.bmjwhere.repository.ResturantRepository;
import com.example.bmjwhere.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class ResturantServiceImpl implements ResturantService {

    private final ResturantRepository resturantRepository;
    private final ResturantImageRepository imageRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    @Override
    public Long register(ResturantDTO resturantDTO) {

        Map<String, Object> entityMap = dtoToEntity(resturantDTO);
        Resturant resturant = (Resturant)entityMap.get("resturant");
        List<ResturantImage> resturantImageList = (List<ResturantImage>) entityMap.get("imgList");


        resturantRepository.save(resturant);
        resturantImageList.forEach(resturantImage -> {
            imageRepository.save(resturantImage);
        });

        return resturant.getRno();
    }

    @Override
    public PageResultDTO<ResturantDTO, Object[]> getList(PageRequestDTO requestDTO) {
        log.info("getList(): " + requestDTO);

        Page<Object[]> result = resturantRepository.searchPage(
                requestDTO.getType(),
                requestDTO.getKeyword(),
                requestDTO.getPageable(Sort.by("rno").descending()) );

        Function<Object[], ResturantDTO> fn = (arr -> entitiesToDTO(
                (Resturant) arr[0],
                (List<ResturantImage>)(Arrays.asList((ResturantImage)arr[1])),
                (Double)arr[2],
                (Long)arr[3])
        );
        log.info(fn);

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public PageResultDTO<ResturantDTO, Object[]> getJList(PageRequestDTO requestDTO) {
        log.info("getJList(): " + requestDTO);



        return null;
    }

    @Override
    public ResturantDTO getResturant(Long rno) {
        List<Object[]> result = resturantRepository.getResturantWithAll(rno);

        Resturant resturant = (Resturant)result.get(0)[0];

        List<ResturantImage> resturantImageList = new ArrayList<>();

        result.forEach(arr -> {
            ResturantImage resturantImage = (ResturantImage)arr[1];
            resturantImageList.add(resturantImage);
        });

        Double avg = (Double)result.get(0)[2];
        Long reviewCnt = (Long)result.get(0)[3];

        return entitiesToDTO(resturant, resturantImageList, avg, reviewCnt);
    }

    @Transactional
    @Override
    public void removeWithReplise(Long rno) {
        log.info("rno: " + rno);


        reviewRepository.deleteByReviews(rno);

        imageRepository.deleteByImages(rno);

        resturantRepository.deleteById(rno);
    }

    @Transactional
    @Override
    public void modify(ResturantDTO resturantDTO) {
        log.info(resturantDTO.getRno());
        Resturant resturant = resturantRepository.getOne(resturantDTO.getRno());

        resturant.changeTitle(resturantDTO.getTitle());
        resturant.changeAddress(resturantDTO.getAddress());
        resturant.changePhoneNumber(resturantDTO.getPhoneNumber());
        resturant.changeType2(resturantDTO.getType2());
        resturant.changeOperatingTime(resturantDTO.getOperatingTime());

        resturantRepository.save(resturant);
    }
}

