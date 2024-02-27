package com.example.shooperapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.shooperapp.dao.ProductDao;
import com.example.shooperapp.dao.ReviewDao;
import com.example.shooperapp.dto.ResponseStructure;
import com.example.shooperapp.entity.Product;
import com.example.shooperapp.entity.Review;
import com.example.shooperapp.entity.Users;
import com.example.shooperapp.exception.InvalidActionException;
import com.example.shooperapp.exception.ProductNotFoundException;
import com.example.shooperapp.exception.ReviewNotFoundException;
import com.example.shooperapp.exception.UserDoesNotPresentException;

@Service
public class ReviewService {

	@Autowired
	private UsersService userService;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ReviewDao reviewDao;

	public ResponseEntity<ResponseStructure<Review>> saveReview(Review review, int id, int product_id) {

		Users valid_user = userService.validUser(id);
		if (valid_user != null) {

			if (valid_user.getRole().equalsIgnoreCase("Customer")) {

				Product valid_product = productDao.findProduct(product_id);

				if (valid_product != null) {

					// set the review to the product
					review.setProduct(valid_product);

					Review saved_review = reviewDao.saveReview(review);

					ResponseStructure<Review> responseStructure = new ResponseStructure();
					responseStructure.setStatusCode(HttpStatus.CREATED.value());
					responseStructure.setMessage("Review Created successfully");
					responseStructure.setData(saved_review);

					return new ResponseEntity<ResponseStructure<Review>>(responseStructure, HttpStatus.CREATED);
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

	public ResponseEntity<ResponseStructure<List<Review>>> getReviews(int id, int product_id) {
		
		Users valid_user = userService.validUser(id);
		if (valid_user != null) {

			if (valid_user.getRole().equalsIgnoreCase("Merchant")) {

				Product valid_product = productDao.findProduct(product_id);

				if (valid_product != null) {

				 List<Review> review_list=valid_product.getReviews();

					ResponseStructure<List<Review>> responseStructure = new ResponseStructure();
					responseStructure.setStatusCode(HttpStatus.OK.value());
					responseStructure.setMessage("sucess");
					responseStructure.setData(review_list);

					return new ResponseEntity<ResponseStructure<List<Review>>>(responseStructure, HttpStatus.OK);
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

	public ResponseEntity<ResponseStructure<Review>> deleteReview(int id, int review_id) {
		
		Users valid_user = userService.validUser(id);
		if (valid_user != null) {

			if (valid_user.getRole().equalsIgnoreCase("Merchant")) {

				Review valid_review = reviewDao.findReview(review_id);

				if (valid_review != null) {

				reviewDao.deleteReview(valid_review);

					ResponseStructure<Review> responseStructure = new ResponseStructure();
					responseStructure.setStatusCode(HttpStatus.OK.value());
					responseStructure.setMessage("sucess");
					responseStructure.setData(valid_review);

					return new ResponseEntity<ResponseStructure<Review>>(responseStructure, HttpStatus.OK);
				} else {
					throw new ReviewNotFoundException();
				}
			} else {
				throw new InvalidActionException("Actionis not valid to perform for " + valid_user.getRole());
			}

		} else {
			throw new UserDoesNotPresentException("User with " + id + " does not presnet in database");
		}
	}

}
