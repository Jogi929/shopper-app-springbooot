package com.example.shooperapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.shooperapp.dao.OrderDao;
import com.example.shooperapp.dto.ResponseStructure;
import com.example.shooperapp.entity.Orders;
import com.example.shooperapp.entity.Users;
import com.example.shooperapp.exception.InvalidActionException;
import com.example.shooperapp.exception.UserDoesNotPresentException;

@Service
public class OrderService {

	@Autowired
	UsersService userService;
	@Autowired
	OrderDao orderDao;

	public ResponseEntity<ResponseStructure<Orders>> saveOrder(Orders order, int id) {
		Users valid_user = userService.validUser(id);

		if (valid_user != null) {

			if (valid_user.getRole().equalsIgnoreCase("Customer")) {

				// setting the order to the user
				order.setUser(valid_user);

				Orders saved_order = orderDao.saveOrder(order);

				ResponseStructure<Orders> responseStructure = new ResponseStructure();
				responseStructure.setStatusCode(HttpStatus.CREATED.value());
				responseStructure.setMessage("Order is Saved in the dataBase");
				responseStructure.setData(saved_order);

				return new ResponseEntity<ResponseStructure<Orders>>(responseStructure, HttpStatus.CREATED);

			} else {
				throw new InvalidActionException("Actionis not valid to perform for " + valid_user.getRole());
			}

		} else {
			throw new UserDoesNotPresentException("User with " + id + " does not presnet in database");
		}

	}

	public ResponseEntity<ResponseStructure<List<Orders>>> getAllOrders(int id) {
		Users valid_user = userService.validUser(id);

		if (valid_user != null) {

			if (valid_user.getRole().equalsIgnoreCase("Customer")) {

				List<Orders> order_list= valid_user.getOrders();

				ResponseStructure<List<Orders>> responseStructure = new ResponseStructure();
				responseStructure.setStatusCode(HttpStatus.OK.value());
				responseStructure.setMessage("Order is Saved in the dataBase");
				responseStructure.setData(order_list);

				return new ResponseEntity<ResponseStructure<List<Orders>>>(responseStructure, HttpStatus.CREATED);

			} else {
				throw new InvalidActionException("Actionis not valid to perform for " + valid_user.getRole());
			}

		} else {
			throw new UserDoesNotPresentException("User with " + id + " does not presnet in database");
		}
	}

}
