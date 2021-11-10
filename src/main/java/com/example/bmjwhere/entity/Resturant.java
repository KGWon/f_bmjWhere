package com.example.bmjwhere.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Resturant extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String title;

    private String address;

    private String phoneNumber;

    private String type2;

    private String operatingTime;



    public void changeTitle(String title) {
        this.title = title;
    }
    public void changeAddress(String address){ this.address = address;}
    public void changePhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber;}
    public void changeType2(String type2){ this.type2 = type2;}
    public void changeOperatingTime(String operatingTime){ this.operatingTime = operatingTime;}


}