package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Order;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {

    public int insert(Order order);

    public int updateById(Order order);

    public Order queryOrderById(Long id);

    public List<Order> queryOrder();

    public int delete(Long id);
}
