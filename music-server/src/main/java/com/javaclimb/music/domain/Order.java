package com.javaclimb.music.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Order {

    private Long id;    //订单id: out_trade_no 不能重复

    private Long userId; //用户ID

    private String orderSubject;    //订单名称

    private String tradeNo;   //支付宝交易凭证号

    private BigDecimal orderTotalAmount;    //订单交易金额 单位：元

    private Long buyer;     //购买者支付宝唯一ID

    private String purchaseTime;    //VIP购买时长 7天 15天 30天 60天 180天 360天

    private LocalDateTime validUntil;   //截止日期

    private Integer orderStatus;    //交易状态 （0：未完成，1：交易成功）

    private Date gmtPayment;   //购买者付款时间

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
