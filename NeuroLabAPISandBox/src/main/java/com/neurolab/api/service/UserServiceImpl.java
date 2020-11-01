package com.neurolab.api.service;

import com.neurolab.api.model.User;
import com.neurolab.api.repository.UserRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {
  @Autowired
  UserRepository userRepository;
  
  public User getUserName(Long userId) {
    User user = null;
    user = this.userRepository.getUserName(userId);
    return user;
  }
}
