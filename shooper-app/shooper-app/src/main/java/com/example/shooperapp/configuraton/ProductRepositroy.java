package com.example.shooperapp.configuraton;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shooperapp.entity.Product;

public interface ProductRepositroy extends JpaRepository<Product, Integer> {

}
