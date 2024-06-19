package com.javaclimb.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.easysdk.factory.Factory;
import com.javaclimb.music.config.AliPayConfig;
import com.javaclimb.music.dao.OrderMapper;
import com.javaclimb.music.domain.AliPay;
import com.javaclimb.music.domain.Order;
import com.javaclimb.music.service.OrderService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("alipay")
@Transactional(rollbackFor = Exception.class)
public class AliPayController {

    @Autowired
    AliPayConfig aliPayConfig;

    @Autowired
    private OrderService orderService;
    private static final String GATEWAY_URL ="https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT ="JSON";
    private static final String CHARSET ="utf-8";
    private static final String SIGN_TYPE ="RSA2";
    @GetMapping("/pay") // &subject=xxx&traceNo=xxx&totalAmount=xxx
    public void pay(AliPay aliPay, HttpServletResponse httpResponse) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAlipayPublicKey(), SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();

        //异步接收地址，仅支持http/https，公网可访问
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        //同步跳转地址，仅支持http/https
        if(aliPay.getReturnSystem().equals("manger")) {
            request.setReturnUrl("http://localhost:8080/");
        } else if (aliPay.getReturnSystem().equals("client")) {
            request.setReturnUrl("http://localhost:8081/");
        }

//        request.setBizContent("{\"out_trade_no\":\"" + aliPay.getOutTraceNo() + "\","
//                + "\"total_amount\":\"" + aliPay.getTotalAmount() + "\","
//                + "\"subject\":\"" + aliPay.getSubject() + "\","
//                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        JSONObject bizContent = new JSONObject();

        System.out.println("===============");
        System.out.println("outTraceNo=" + aliPay.getOutTraceNo());
        System.out.println("totalAmount=" + aliPay.getTotalAmount());
        System.out.println("subject=" + aliPay.getSubject());
        System.out.println("===============");

        //商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", aliPay.getOutTraceNo());
        //支付金额，最小值0.01元
        bizContent.put("total_amount", aliPay.getTotalAmount());
        //订单标题，不可使用特殊符号
        bizContent.put("subject", aliPay.getSubject());
        //电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());
        String form = "";
        try {
            // 调用SDK生成表单
            form = alipayClient.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        // 直接将完整的表单html输出到页面
        httpResponse.getWriter().write(form);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }
    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                 System.out.println(name + " = " + request.getParameter(name));
            }

            String outTradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");
            String buyerId = params.get("buyer_id");
            // 支付宝验签
            if (Factory.Payment.Common().verifyNotify(params)) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));

                // 更新订单未已支付
                Order order = orderService.queryOrderById(Long.parseLong(outTradeNo));
                order.setTradeNo(alipayTradeNo);
                order.setBuyer(Long.parseLong(buyerId));
                order.setOrderStatus(1);
                Date payTime = DateUtils.parseDate(gmtPayment, "yyyy-MM-dd HH:mm:ss");
                order.setGmtPayment(payTime);
                orderService.updateOrder(order);
            }
        }
        return "success";
    }
}
