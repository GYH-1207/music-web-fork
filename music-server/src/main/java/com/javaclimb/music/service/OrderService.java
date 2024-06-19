package com.javaclimb.music.service;

import com.javaclimb.music.domain.Order;
import com.javaclimb.music.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderService {

    public Object createOrder(Order order);

    public Object updateOrder(Order order);

    public Order queryOrderById(Long id);

    public List<OrderDto> queryOrder();

    public int deleteOrder(Long id);
}
