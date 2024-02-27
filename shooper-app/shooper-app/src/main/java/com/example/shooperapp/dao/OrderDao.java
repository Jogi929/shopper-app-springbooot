package com.example.shooperapp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.shooperapp.configuraton.OrderRepository;
import com.example.shooperapp.entity.Orders;

@Repository
public class OrderDao {
	
	@Autowired
	OrderRepository orderRepository; 

	public Orders saveOrder(Orders order) {
		return orderRepository.save(order);
	}

	public void saveAll(List<Orders> orders) {
		
		orderRepository.saveAll(orders);
	}

}
