package com.example.demo.model.sell.dto;

import lombok.Data;

@Data
public class SellRegReadDto {
    private String prodOrderNo; // 상품주문번호 1(0)
    private String orderNo; // 주문번호 2(1)
    private String prodNo; // 상품번호 16(15)
    private String prodName; // 상품명 17(16)
    private String optionInfo; // 옵션정보 19(18)
    private int unit; // 수량 21(20)
    private int shipping; // 배송비합계 35(34)
    private int amount; // 정산예정금액 54(53)
}
