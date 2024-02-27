package com.example.shooperapp.configuraton;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shooperapp.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

}
