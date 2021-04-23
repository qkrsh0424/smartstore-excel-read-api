package com.example.demo.model.sell.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Data
@Entity
@Table(name = "sell_item")
public class SellItemPureEntity {
    // @GeneratedValue(generator = "uuid2")  
    // @GenericGenerator(name = "uuid2", strategy = "uuid2") 
    // @Column(name = "uuid", columnDefinition = "BINARY(16)")
    // @Type(type="uuid-char")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "mall_name")
    private String mallName; // 상품주문번호 1(0)
    @Column(name = "prod_order_no")
    private String prodOrderNo; // 상품주문번호 1(0)
    @Column(name = "order_no")
    private String orderNo; // 주문번호 2(1)
    @Column(name = "prod_no")
    private String prodNo; // 상품번호 16(15)
    @Column(name = "prod_name")
    private String prodName; // 상품명 17(16)
    @Column(name = "option_info")
    private String optionInfo; // 옵션정보 19(18)
    @Column(name = "unit")
    private int unit; // 수량 21(20)
    @Column(name = "shipping")
    private int shipping; // 배송비합계 35(34)
    @Column(name = "amount")
    private int amount; // 정산예정금액 54(53)
    @Column(name = "reg_date")
    private Date regDate;
    @Column(name="confirm_date")
    private Date confirmDate;
    @Column(name="confirmed")
    private int confirmed;
}
