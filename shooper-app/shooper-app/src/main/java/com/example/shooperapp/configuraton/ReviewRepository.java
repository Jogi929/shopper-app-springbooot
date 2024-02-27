package com.example.shooperapp.configuraton;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.shooperapp.entity.Review;

import jakarta.transaction.Transactional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	
}
