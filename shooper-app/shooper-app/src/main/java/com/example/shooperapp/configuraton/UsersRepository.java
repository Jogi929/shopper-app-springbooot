package com.example.shooperapp.configuraton;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shooperapp.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

	
}
