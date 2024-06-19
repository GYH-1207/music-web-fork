package com.javaclimb.music.controller;

import com.javaclimb.music.domain.Order;
import com.javaclimb.music.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public Object createOrder(HttpServletRequest httpServletRequest) {
        String userId = httpServletRequest.getParameter("userId");
        String orderSubject = httpServletRequest.getParameter("orderSubject");
        String orderTotalAmount = httpServletRequest.getParameter("orderTotalAmount");
        String purchaseTime = httpServletRequest.getParameter("purchaseTime");

        Order order = new Order();
        order.setUserId(Long.parseLong(userId));
        order.setOrderSubject(orderSubject);
        order.setOrderTotalAmount(new BigDecimal(orderTotalAmount));
        order.setPurchaseTime(purchaseTime);

        return orderService.createOrder(order);
    }

    @PostMapping("/alipay/update")
    public Object updateOrder(HttpServletRequest httpServletRequest) {
        String id = httpServletRequest.getParameter("id");
        String orderSubject = httpServletRequest.getParameter("orderSubject");
        String tradeNo = httpServletRequest.getParameter("tradeNo");
        String orderTotalAmount = httpServletRequest.getParameter("orderTotalAmount");
        String buyer = httpServletRequest.getParameter("buyer");
        String purchaseTime = httpServletRequest.getParameter("purchaseTime");
        String orderStatus = httpServletRequest.getParameter("orderStatus");
        String payTime = httpServletRequest.getParameter("payTime");

        Order order = new Order();
        order.setId(Long.parseLong(id));
        order.setOrderSubject(orderSubject);
        order.setTradeNo(tradeNo);
        order.setOrderTotalAmount(new BigDecimal(orderTotalAmount));
        order.setBuyer(Long.parseLong(buyer));
        order.setPurchaseTime(purchaseTime);
        order.setOrderStatus(Integer.parseInt(orderStatus));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            order.setGmtPayment(format.parse(payTime));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return orderService.updateOrder(order);
    }

    @GetMapping("/getOrderById")
    public Order getOrderById(Long id) {
        return orderService.queryOrderById(id);
    }

    @GetMapping("/details")
    public Object getDetails() {
        return orderService.queryOrder();
    }

    @GetMapping("/delete")
    public Object delete(Long id) {
        return orderService.deleteOrder(id);
    }
}
