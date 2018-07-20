package com.jezh.springmvcjpa.service;


import com.jezh.springmvcjpa.model.UserProfile;

import java.util.List;


public interface UserProfileService {

	UserProfile findById(int id);

	UserProfile findByType(String type);
	
	List<UserProfile> findAll();
	
}
