package com.services.direct.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.security.User;
import com.services.direct.exception.CustomException;
import com.services.direct.repo.CompanyRepository;
import com.services.direct.repo.UserRepository;
import com.services.direct.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service(value = "userService")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

	private static Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	private final UserRepository userRepository;
	
	@Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	log.info("loadUserByUsername : {}" + username);
        Objects.requireNonNull(username);
        User user = userRepository.findByUsername(username);
        log.info("return user : {}" + user);
        if (user != null) {
        	return user;
        }
        throw new UsernameNotFoundException(username);
    }
   
    @Override
    public List<User> findAll() {
		List<User> list = new ArrayList<User>();
		userRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

    public User search(String username) throws CustomException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
          throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND.name());
        }
        return user;
      }
    
	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void delete(String username) {
		userRepository.deleteByUsername(username);
	}
}
