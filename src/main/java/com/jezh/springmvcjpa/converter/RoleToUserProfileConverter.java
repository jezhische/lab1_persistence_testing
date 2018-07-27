package com.jezh.springmvcjpa.converter;

import com.jezh.springmvcjpa.model.UserProfile;
import com.jezh.springmvcjpa.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * A converter class used in views to map id's to actual userProfile objects.
 */
//@Component
public class RoleToUserProfileConverter /*implements Converter<Object, UserProfile>*/ {

//	@Autowired
//	UserProfileService userProfileService;

	/**
	 * Gets UserProfile by Id
	 * @see Converter#convert(Object)
	 */
//	public UserProfile convert(Object element) {
//		Integer id = Integer.parseInt((String)element);
//		UserProfile profile= userProfileService.findById(id);
//		System.out.println("Profile : "+profile);
//		return profile;
//	}
	
}