package com.abcde.cultureStay.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Program {
    int programNum;
    String userid;
    String title;
    String content;
    String address;
    String detailed_address;
    String postcode;
    int price;
    String start_date;
    String end_date;
    String inputdate;
    int hits;

    //추출한 이미지파일 경로
    String imagePath1;
    String imagePath2;


}