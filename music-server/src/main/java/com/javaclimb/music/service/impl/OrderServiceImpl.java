package com.javaclimb.music.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.dao.OrderMapper;
import com.javaclimb.music.domain.Consumer;
import com.javaclimb.music.domain.Order;
import com.javaclimb.music.dto.OrderDto;
import com.javaclimb.music.dto.VipValidDto;
import com.javaclimb.music.service.ConsumerService;
import com.javaclimb.music.service.OrderService;
import com.javaclimb.music.utils.Consts;
import com.javaclimb.music.utils.DateTimeParser;
import com.javaclimb.music.utils.RedisPrefixUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ConsumerService consumerService;

    @Override
    public Object createOrder(Order order) {
        JSONObject jsonObject = new JSONObject();

        // id OrderSubject orderTotalAmount purchaseTime orderStatus
        Long id = (long) Math.abs(UUID.randomUUID().hashCode());
        String orderSubject = order.getOrderSubject() + "-" + System.currentTimeMillis();

        order.setId(id);
        order.setOrderSubject(orderSubject);
        order.setOrderStatus(0);
        // jdbc
        boolean flag = orderMapper.insert(order) > 0;

        // 打印日志
        printLogs(order, "create", flag);

        if (flag) {
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "创建成功");
            jsonObject.put("data", id);
            return jsonObject;
        }

        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "创建失败");
        return jsonObject;
    }

    /**
     * @param order
     */
    @Override
    public Object updateOrder(Order order) {
        JSONObject jsonObject = new JSONObject();

        Long userId = order.getUserId();
        LocalDateTime now = LocalDateTime.now();
        String purchaseTime = order.getPurchaseTime();

        // redis的key
        String validUntilKey = RedisPrefixUtils.generatorVipValidUntilKey(userId.toString());
        String proValidUntilData = stringRedisTemplate.opsForValue().get(validUntilKey);

        LocalDateTime validUntilRes = null; //到期日期
        String purchaseTimeRes = null;  //过期时间 秒
        if (proValidUntilData != null && !proValidUntilData.equals("")) {
            JSONObject vipValidDtoJson = JSONObject.parseObject(proValidUntilData);
            LocalDateTime validUntil = LocalDateTime.parse(vipValidDtoJson.getString("validUntil"));

            // 判断 validUntil 是否在现在之后，还没过期
            if (validUntil.isAfter(now)) {
                // 到期日期 = 剩余的时间 + now() + 充值天数
                Duration remainingDays = Duration.between(now, validUntil);
                long remainingSeconds = remainingDays.getSeconds();

                purchaseTime = dayConvertSeconds(purchaseTime);

                //过期时间 = 剩余时间 + 充值天数
                purchaseTimeRes = String.valueOf(remainingSeconds + Long.parseLong(purchaseTime));
                validUntilRes = now.plusSeconds(remainingSeconds).plusSeconds(Long.parseLong(purchaseTime));
            }
        } else {
            purchaseTimeRes = dayConvertSeconds(purchaseTime);
            validUntilRes = now.plusSeconds(Long.parseLong(purchaseTimeRes));
        }

        order.setValidUntil(validUntilRes);

        VipValidDto vipValidDto = new VipValidDto();
        vipValidDto.setValidUntil(validUntilRes.toString());
        stringRedisTemplate.opsForValue().set(validUntilKey, JSONObject.toJSONString(vipValidDto), Long.parseLong(purchaseTimeRes), TimeUnit.SECONDS);

        // 插入数据库
        boolean flag = orderMapper.updateById(order) > 0;

        // 打印日志
        printLogs(order, "update", flag);

        if (flag) {
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "修改失败");
        return jsonObject;
    }

    /**
     * 天数转成秒数
     *
     * @param days
     * @return
     */
    public String dayConvertSeconds(String days) {
        // 一天的秒数
        int secondsInADay = 24 * 60 * 60;
        // 将天数转换为秒数;
        return String.valueOf(Integer.parseInt(days) * secondsInADay);
    }

    public Order queryOrderById(Long id) {
        return orderMapper.queryOrderById(id);
    }

    public List<OrderDto> queryOrder() {
        List<Order> orders = orderMapper.queryOrder();
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            Long id = order.getId();
            Long userId = order.getUserId();    //用户ID
            String orderSubject = order.getOrderSubject();  //订单名称
            String tradeNo = order.getTradeNo();    //支付宝交易凭证号
            BigDecimal orderTotalAmount = order.getOrderTotalAmount();  //订单交易金额 单位：元
            Long buyer = order.getBuyer();  //购买者支付宝唯一ID
            String purchaseTime = order.getPurchaseTime();  //VIP购买时长 单位：天
            LocalDateTime validUntil = order.getValidUntil();   //截止日期
            Integer orderStatus = order.getOrderStatus();   //交易状态 （0：未完成，1：交易成功）
            Date gmtPayment = order.getGmtPayment();    //购买者付款时间
            LocalDateTime createTime = order.getCreateTime();
            LocalDateTime updateTime = order.getUpdateTime();

            String idRes = id.toString();
            String userIdRes = userId.toString();
            Consumer consumer = consumerService.selectByPrimaryKey(Math.toIntExact(userId));
            String username = consumer.getUsername();
            orderSubject = orderSubject.split("-")[0];
            String validUntilRes = DateTimeParser.parseDateTime(validUntil);
            String orderStatusRes = orderStatus == 1 ? "交易成功" : "未完成";
            String gmtPaymentRes = DateTimeParser.parseDateTime(gmtPayment);
            String createTimeRes = DateTimeParser.parseDateTime(createTime);
            String updateTimeRes = DateTimeParser.parseDateTime(updateTime);

            OrderDto orderDto = new OrderDto();
            orderDto.setId(idRes);
            orderDto.setUserId(userIdRes);
            orderDto.setUserName(username);
            orderDto.setOrderSubject(orderSubject);
            orderDto.setTradeNo(tradeNo!=null?tradeNo:"空");
            orderDto.setOrderTotalAmount(orderTotalAmount.toString());
            orderDto.setBuyer(buyer!=null?buyer.toString():"空");
            orderDto.setPurchaseTime(purchaseTime);
            orderDto.setValidUntil(validUntilRes!=null?validUntilRes:"空");
            orderDto.setOrderStatus(orderStatusRes);
            orderDto.setGmtPayment(gmtPaymentRes!=null?gmtPaymentRes:"空");
            orderDto.setCreateTime(createTimeRes);
            orderDto.setUpdateTime(updateTimeRes);
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }

    public int deleteOrder(Long id) {
        return orderMapper.delete(id);
    }

    public static void main(String[] args) {

//        System.out.println(parseLocalDateTime());


    }


    public void printLogs(Order order, String logsType, boolean flag) {
        Long id = order.getId();
        String orderSubject = order.getOrderSubject();
        String tradeNo = order.getTradeNo();
        BigDecimal orderTotalAmount = order.getOrderTotalAmount();
        Long buyer = order.getBuyer();
        String purchaseTime = order.getPurchaseTime();
        LocalDateTime validUntil = order.getValidUntil();
        int orderStatus = order.getOrderStatus();
        Date gmtPayment = order.getGmtPayment();
        LocalDateTime createTime = order.getCreateTime();
        LocalDateTime updateTime = order.getUpdateTime();

        String logStr = "";
        if (logsType.equals("create")) {
            logStr = "创建";
        }
        if (logsType.equals("update")) {
            logStr = "修改";
        }

        String logStart = logStr + "订单开始";

        String logEnd = "";
        if (flag) {
            logEnd = logStr + "订单成功";
        } else {
            logEnd = logStr + "订单失败";
        }

        log.info("====" + logStart + "====");

        if (id != null) {
            log.info("ID: " + id);
        }
        if (orderSubject != null && !orderSubject.equals("")) {
            log.info("订单名称: " + orderSubject);
        }
        if (tradeNo != null) {
            log.info("支付宝交易凭证号: " + tradeNo);
        }
        if (orderTotalAmount != null && !BigDecimal.ZERO.equals(orderTotalAmount)) {
            log.info("订单交易金额: " + orderTotalAmount + "元");
        }
        if (buyer != null) {
            log.info("买家在支付宝唯一id: " + buyer);
        }
        if (purchaseTime != null && !purchaseTime.equals("")) {
            log.info("购买vip时间: " + purchaseTime + "天");
        }
        if (validUntil != null) {
            log.info("有效期至: " + validUntil);
        }
        if (orderStatus != -1) {
            log.info("交易状态: " + (orderStatus == 1 ? "交易成功" : "未完成"));
        }
        if (gmtPayment != null) {
            log.info("买家付款时间: " + gmtPayment);
        }
        if (createTime != null) {
            log.info("订单创建时间: " + createTime);
        }
        if (updateTime != null) {
            log.info("订单修改时间: " + updateTime);
        }

        log.info("====" + logEnd + "====");
    }
}