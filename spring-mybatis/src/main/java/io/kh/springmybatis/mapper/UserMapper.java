package io.kh.springmybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.kh.springmybatis.model.User;

@Mapper
public interface UserMapper {
	
	List<User> getAllUsers();
	
	void createUser(User user);
	
	User getOneByUsername(String username);
	
	User getOneByEmail(String email);
	
	int countUserByEmail(String email);
}
