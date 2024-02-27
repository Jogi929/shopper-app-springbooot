package com.example.shooperapp.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.shooperapp.configuraton.ReviewRepository;
import com.example.shooperapp.entity.Product;
import com.example.shooperapp.entity.Review;

@Repository
public class ReviewDao {

	@Autowired
	private ReviewRepository reviewRepository;

	public Review saveReview(Review review) {

		return reviewRepository.save(review);
	}

	public Review findReview(int review_id) {

		Optional<Review> optional = reviewRepository.findById(review_id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

	public void deleteReview(Review valid_review) {
		
		reviewRepository.delete(valid_review);
	}

	 

}
