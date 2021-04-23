package com.example.demo.model.sell.dto;

import java.util.Date;

import lombok.Data;

@Data
public class SellRegReadDto { 
    private String mallName;
    private String prodOrderNo; // 상품주문번호 1(0)
    private String orderNo; // 주문번호 2(1)
    private String prodNo; // 상품번호 6(5)
    private String prodName; // 상품명 7(6)
    private String optionInfo; // 옵션정보 8(7)
    private int unit; // 수량 9(8)
    // private int shipping; // 배송비합계 35(34)
    // private int amount; // 정산예정금액 54(53)
    private Date regDate; // 주문일시 3(0)
}
