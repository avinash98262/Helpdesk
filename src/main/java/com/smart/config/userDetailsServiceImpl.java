package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.dao.userRepository;
import com.smart.entities.User;

public class userDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private userRepository userrepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//fetching user from database; 
		
		User user = userrepository.getUserByName(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("Could not found User!!");
		}
		
		customUserDetails customUserDetails = new customUserDetails(user);
		
		return customUserDetails;
	}
	
	

}
