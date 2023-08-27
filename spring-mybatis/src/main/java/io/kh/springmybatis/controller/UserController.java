package io.kh.springmybatis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.kh.springmybatis.mapper.UserMapper;
import io.kh.springmybatis.model.User;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	// @Autowired  // when use @Autowired on this case we don't need create constructor
	// private UserMapper userMapper;
	
	// for response message as object
	Map<String, Object> obj = new HashMap<>();

	public UserController(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	
	@GetMapping
	public List<User> getAllUsers(){
		return userMapper.getAllUsers();
	}
	
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody User user) {
		
		try {
			
			if(user.getUsername().trim().isEmpty() || user.getEmail().trim().isEmpty()) {
				obj.put("success",false);
				obj.put("message", "Username and Email are required.");
				return ResponseEntity.ok(obj);
			}
			
			if(user.getPassword().trim().isEmpty()) {
				obj.put("success",false);
				obj.put("message", "Password is required.");
				return ResponseEntity.ok(obj);
			}
			
			// check if username already exist
			User usernameExisting = userMapper.getOneByUsername(user.getUsername());
			if(usernameExisting!=null) {
				obj.put("success",false);
				obj.put("message", "Sorry! Username already exist. Please try another once.");
				return ResponseEntity.ok(obj);
			}
			
			// check duplicate email
			User emailExistsing = userMapper.getOneByEmail(user.getEmail());
			if(emailExistsing!=null) {
				obj.put("success",false);
				obj.put("message", "Sorry! Email already exist. Please try another once.");
				return ResponseEntity.ok(obj);
			}
			
			
			userMapper.createUser(user);
			obj.put("success",true);
			obj.put("message", "User has been created successfully.");
			
			return ResponseEntity.status(HttpStatus.CREATED).body(obj);
			
		}catch (Exception e) {

			obj.put("success",false);
			obj.put("message", "User created fail.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(obj);
		}
		
		 
	}
}
