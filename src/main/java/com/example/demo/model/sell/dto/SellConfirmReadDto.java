package com.example.demo.model.sell.dto;

import java.util.Date;

import lombok.Data;

@Data
public class SellConfirmReadDto {
    private String mallName;
    private String prodOrderNo; // 1(0)
    private String orderNo; // 2(1)
    private String prodNo; // 14(13)
    private String prodName; // 15(14)
    private String optionInfo; // 17(16)
    private int unit; // 18(17)
    private int shipping; // 30(29)
    private int amount; // 41(40)
    private Date regDate; // 33(32)
    private Date confirmDate; // 3(2)
}
