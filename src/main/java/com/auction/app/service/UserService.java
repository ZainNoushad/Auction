package com.auction.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auction.app.impl.UserDetailsImpl;
import com.auction.app.model.User;
import com.auction.app.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=userRepository.findByUsername(username);
		if(user==null)
			throw new UsernameNotFoundException("User Not Found");
		
		
		return new UserDetailsImpl(user);
	}

}
