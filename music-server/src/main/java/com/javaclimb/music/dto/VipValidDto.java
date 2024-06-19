package com.javaclimb.music.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VipValidDto {

    private String purchaseTime; //冲了多少天 1天 15天 30天 60天 180天 360天

    private String validUntil; //有效期到哪天
}
