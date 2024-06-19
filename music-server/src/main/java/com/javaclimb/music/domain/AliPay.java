package com.javaclimb.music.domain;

import lombok.Data;

@Data
public class AliPay {
    private String outTraceNo;
    private double totalAmount;
    private String subject;
    private String alipayTraceNo;
    private String returnSystem;
}

