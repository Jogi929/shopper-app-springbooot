package com.example.shooperapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.shooperapp.dao.UserDao;
import com.example.shooperapp.dto.ResponseStructure;
import com.example.shooperapp.entity.Users;

@Service
public class UsersService {

	@Autowired
	private UserDao usersDao; 
	
	public ResponseEntity<ResponseStructure<Users>> saveUser(Users user) {
		Users saved_user= usersDao.saveUser(user);
		
		ResponseStructure<Users> responseStructure=new ResponseStructure();
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		responseStructure.setMessage("User is Created with "+saved_user.getRole()+" role");
		responseStructure.setData(saved_user);
		
		return new ResponseEntity<ResponseStructure<Users>>(responseStructure,HttpStatus.CREATED);
		
	}
	
	
	public Users validUser(int id){
		
		return usersDao.validUser(id);
	}

}
