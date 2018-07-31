package com.jezh.springmvcjpa.dao;


import com.jezh.springmvcjpa.model.UserProfile;

import java.util.List;


public interface UserProfileDao {

	List<UserProfile> findAll();

	UserProfile findByType(String type);

	UserProfile findById(int id);
}
