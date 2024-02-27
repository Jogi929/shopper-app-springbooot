package com.example.shooperapp.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.shooperapp.configuraton.UsersRepository;
import com.example.shooperapp.entity.Users;

@Repository
public class UserDao {
	
	@Autowired
	private UsersRepository usersRepository;

	public  Users saveUser(Users user) {
		return usersRepository.save(user);
	}

	public Users validUser(int id) {
		Optional<Users> optional=usersRepository.findById(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}

}
