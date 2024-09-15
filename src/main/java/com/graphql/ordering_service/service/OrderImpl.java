package com.graphql.ordering_service.service;

import com.graphql.ordering_service.entity.Order;
import com.graphql.ordering_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderImpl {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByCategory(String category){
        return orderRepository.findByCategory(category);
    }

    //update stock of  order
    public Order updateStock(int id, int stock) {
        Order existinngOrder= orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"+id));
        existinngOrder.setStock(stock);
        return orderRepository.save(existinngOrder);
    }

    //add stock of  order
    public Order addStock(int id, int quantity) {
        Order existinngOrder= orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"+id));
        existinngOrder.setStock(existinngOrder.getStock()+quantity);
        return orderRepository.save(existinngOrder);
    }
}
