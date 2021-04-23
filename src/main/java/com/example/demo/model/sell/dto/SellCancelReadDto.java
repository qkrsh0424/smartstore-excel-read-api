package com.example.demo.model.sell.dto;

import java.util.Date;

import lombok.Data;

@Data
public class SellCancelReadDto {
    private String mallName;
    private String prodOrderNo;
    private String orderNo;
    private String prodNo;
    private String prodName;
    private String optionInfo;
    private int unit;
    private int shipping;
    private int amount;
    private Date regDate;
    private Date cancelDate;
}
