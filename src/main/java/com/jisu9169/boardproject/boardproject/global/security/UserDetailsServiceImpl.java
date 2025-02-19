package com.jisu9169.boardproject.boardproject.global.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jisu9169.boardproject.boardproject.domain.user.UserRepository;
import com.jisu9169.boardproject.boardproject.domain.user.entity.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Users user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));
		return new UserDetailsImpl(user);
	}
}
