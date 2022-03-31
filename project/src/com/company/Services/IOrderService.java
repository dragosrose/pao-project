package com.company.Services;

import com.company.Entities.Order;

import java.util.Set;

public interface IOrderService {

    void addOrder(Order order);
    Set<Order> getOrders();
    Order getOrderById(int id);
    void deleteOrderById(int id);


}
