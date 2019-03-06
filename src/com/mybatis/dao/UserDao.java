package com.mybatis.dao;

import com.mybatis.pojo.User;

public interface UserDao {
	public User findUserById(int id);
}
