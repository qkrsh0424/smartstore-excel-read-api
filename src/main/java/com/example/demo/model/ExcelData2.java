package com.example.demo.model;

import lombok.Data;

@Data
public class ExcelData2 {
    private String buyer; // 구매자명 9
    private String reciever; // 수취인명 11
    private String prodNo; // 상품번호 16
    private String prodName; // 상품명 17
    private String optionInfo; // 옵션정보 19
    private int unit; // 수량 21
    private String recieverContact1; // 수취인 연락처1 41 
    private String recieverContact2; // 수취인 연락처2 42
    private String destination; // 배송지 43
    private String buyerContact; // 구매자 연락처 44
    private String zipcode; // 우편번호 45
    private String deliveryMessage; // 배송메세지 46
    
}

// 구매자명 9
// 수취인명 11
// 상품번호 16
// 상품명 17
// 옵션정보 19
// 수량 21
// 수취인 연락처1 41
// 수취인 연락처2 42
// 배송지 43
// 구매자 연락처 44
// 우편번호 45
// 배송메세지 46