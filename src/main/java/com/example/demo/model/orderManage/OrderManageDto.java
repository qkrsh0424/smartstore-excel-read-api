package com.example.demo.model.orderManage;

import java.util.List;

import lombok.Data;

@Data
public class OrderManageDto implements Comparable<OrderManageDto>{
    private String Uuid;
    private String prodName;
    private String optionInfo;
    private String prodFullName;
    private int unit;
    private List<OrdererDto> ordererList;
    @Override
    public int compareTo(OrderManageDto o) {
        // TODO Auto-generated method stub
        return this.prodFullName.compareTo(o.prodFullName);
    }
}
