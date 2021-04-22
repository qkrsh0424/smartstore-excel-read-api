package com.example.demo.model.sell.proj;


public interface SellItemGroupProj {
    String getMallName();
    String getProdNo();
    String getProdName();
    String getOptionInfo();
    int getUnitSum();
    int getShippingSum();
    int getAmountSum();

    // private String mallName;
    // private String prodNo; // 상품번호 16(15)
    // private String prodName; // 상품명 17(16)
    // private String optionInfo; // 옵션정보 19(18)
    // private int unitSum; // 수량 21(20)
    // private int shippingSum; // 배송비합계 35(34)
    // private int amountSum; // 정산예정금액 54(53)

}
