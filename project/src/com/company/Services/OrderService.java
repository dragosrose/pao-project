package com.company.Services;

import com.company.Entities.Order;
import com.company.Repositories.Repository;

import java.util.Set;

public class OrderService implements IOrderService{
    private static OrderService instance;

    private Repository<Order> orderRepository = new Repository<>();

    private OrderService() {};

    public static OrderService getInstance(){
        if(instance == null)
            instance = new OrderService();
        return instance;
    }

    @Override
    public void addOrder(Order order) {
        this.orderRepository.create(order);
    }

    @Override
    public Set<Order> getOrders() {
        return this.orderRepository.read();
    }

    @Override
    public Order getOrderById(int id) {
        Set<Order> orders = getOrders();

        for(Order i : orders){
            if(i.getId() == id)
                return i;
        }
        return null;
    }

    @Override
    public void deleteOrderById(int id) {
        Set<Order> orders = getOrders();

        for(Order i : orders){
            if(i.getId() == id){
                this.orderRepository.delete(id);
            }

        }
    }
}
