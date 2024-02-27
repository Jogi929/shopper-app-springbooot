package com.example.shooperapp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.shooperapp.configuraton.ProductRepositroy;
import com.example.shooperapp.entity.Orders;
import com.example.shooperapp.entity.Product;

@Repository
public class ProductDao {
	
	@Autowired
	ProductRepositroy productRepositroy;

	public Product saveProduct(Product product) {
		
		return productRepositroy.save(product);
		
	}

	public Product findProduct(int product_id) {
		
		Optional<Product> optional= productRepositroy.findById(product_id);
		
		if(!optional.isEmpty()) {
			return optional.get();
		}else {
			return null;
		}
	}

	public void delteProduct(int product_id) {
			 productRepositroy.deleteById(product_id);
	}
	
	
	
	
	

}
