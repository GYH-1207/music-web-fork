package com.javaclimb.music.dto;

import lombok.Data;

@Data
public class OrderDto {
    private String id;    //订单id out_trade_no 不能重复

    private String userId;  //用户ID

    private String userName; //用户名

    private String orderSubject;    //订单名称

    private String tradeNo;   //支付宝交易凭证号

    private String orderTotalAmount;    //订单交易金额 单位：元

    private String buyer;     //购买者支付宝唯一ID

    private String purchaseTime;    //VIP购买时间 7天 15天 30天 60天 180天 360天

    private String validUntil;   //截止日期

    private String orderStatus;    //交易状态 （0：未完成，1：交易成功）

    private String gmtPayment;   //购买者付款时间

    private String createTime;

    private String updateTime;
}
