package com.mybatis.dao;

import com.mybatis.pojo.User;

public interface UserMapping {
	public User findUserById(int id);
}
