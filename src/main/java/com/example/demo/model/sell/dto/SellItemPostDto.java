package com.example.demo.model.sell.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class SellItemPostDto {
    private List<SellItemGetDto> data;
    private String mallName;
    private Date regDate;
}
