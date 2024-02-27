package com.example.shooperapp.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.shooperapp.dao.OrderDao;
import com.example.shooperapp.dao.ProductDao;
import com.example.shooperapp.dto.ResponseStructure;
import com.example.shooperapp.entity.Orders;
import com.example.shooperapp.entity.Product;
import com.example.shooperapp.entity.Users;
import com.example.shooperapp.exception.InvalidActionException;
import com.example.shooperapp.exception.ProductNotFoundException;
import com.example.shooperapp.exception.UserDoesNotPresentException;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private UsersService userService;
	
	@Autowired
	private OrderDao orderDao;

	public ResponseEntity<ResponseStructure<Product>> saveProduct(Product product, int id) {

		Users valid_user = userService.validUser(id);

		if (valid_user != null) {

			if (valid_user.getRole().equalsIgnoreCase("Merchant")) {

				product.setUser(valid_user);
				Product saved_product = productDao.saveProduct(product);

				ResponseStructure<Product> responseStructure = new ResponseStructure();
				responseStructure.setStatusCode(HttpStatus.CREATED.value());
				responseStructure.setMessage("Product is Saved in the dataBase");
				responseStructure.setData(saved_product);

				return new ResponseEntity<ResponseStructure<Product>>(responseStructure, HttpStatus.CREATED);

			} else {
				throw new InvalidActionException("Actionis not valid to perform for " + valid_user.getRole());
			}

		} else {
			throw new UserDoesNotPresentException("User with " + id + " does not presnet in database");
		}

	}

	public ResponseEntity<ResponseStructure<Product>> updateProduct(Product product, int id, int product_id) {

		Users valid_user = userService.validUser(id);

		if (valid_user != null) {

			if (valid_user.getRole().equalsIgnoreCase("Merchant")) {

				Product valid_product = productDao.findProduct(product_id);

				if (valid_product != null) {
					if (product.getName() != null) {
						valid_product.setName(product.getName());
					}
					if (product.getPrice() != 0.0) {
						valid_product.setPrice(product.getPrice());
					}
					if (product.getCategory() != null) {
						valid_product.setCategory(product.getCategory());
					}

					Product updated_product = productDao.saveProduct(valid_product);

					ResponseStructure<Product> responseStructure = new ResponseStructure();
					responseStructure.setStatusCode(HttpStatus.OK.value());
					responseStructure.setMessage("Product is Updated in the dataBase");
					responseStructure.setData(updated_product);

					return new ResponseEntity<ResponseStructure<Product>>(responseStructure, HttpStatus.OK);
				} else {
					throw new ProductNotFoundException();
				}
			} else {
				throw new InvalidActionException("Actionis not valid to perform for " + valid_user.getRole());
			}

		} else {
			throw new UserDoesNotPresentException("User with " + id + " does not presnet in database");
		}

	}

	public ResponseEntity<ResponseStructure<Product>> deleteProduct(int id, int product_id) {

		Users valid_user = userService.validUser(id);
		if (valid_user != null) {

			if (valid_user.getRole().equalsIgnoreCase("Merchant")) {

				Product valid_product = productDao.findProduct(product_id);

				if (valid_product != null) {
					
					List<Orders> orders=valid_product.getOrders();
					for(int i=0;i<orders.size();i++) {
						orders.get(i).getProducts().removeAll(Arrays.asList(valid_product));
					}
					orderDao.saveAll(orders);
					
					productDao.delteProduct(product_id);

					ResponseStructure<Product> responseStructure = new ResponseStructure();
					responseStructure.setStatusCode(HttpStatus.OK.value());
					responseStructure.setMessage("Deleted Sucessfully");
					responseStructure.setData(valid_product);

					return new ResponseEntity<ResponseStructure<Product>>(responseStructure, HttpStatus.OK);
				} else {
					throw new ProductNotFoundException();
				}
			} else {
				throw new InvalidActionException("Actionis not valid to perform for " + valid_user.getRole());
			}

		} else {
			throw new UserDoesNotPresentException("User with " + id + " does not presnet in database");
		}

	}

}
